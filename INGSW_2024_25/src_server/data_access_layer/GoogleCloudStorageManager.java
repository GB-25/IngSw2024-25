package data_access_layer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import data_access_layer.interfaces.StorageManagerInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.FileNotFoundException;

public class GoogleCloudStorageManager implements StorageManagerInterface{
    private static final String BUCKET_NAME = "foto-ingsw-2024-25";
    private static final String CREDENTIALS_PATH = "INGSW_2024_25/scientific-base-449814-j0-e3a3cf2780c9.json";
    private final Storage storage;

    public GoogleCloudStorageManager() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(CREDENTIALS_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    
    @Override
    public String uploadFile(String fileName, String base64Data) throws IOException {
        // Decodifica la stringa Base64 per ottenere i byte originali
        byte[] fileBytes = Base64.getDecoder().decode(base64Data);
        
        // Costruisci l'objectName: ad esempio, inserisci in una "cartella" chiamata "immobili"
        String objectName = "immobili/" + fileName;
        
        // Prepara l'oggetto Blob per Cloud Storage
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                    .setContentType("image/jpeg")
                                    .build();
        
        // Carica il file su Cloud Storage
        storage.create(blobInfo, fileBytes);
        
        // Costruisci e restituisci l'URL pubblico del file
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + objectName;
    }
    
    
    @Override
    public String downloadImageAsBase64(String fileName) throws IOException {
        // Costruisci l'objectName: ad esempio, in una "cartella" chiamata "immobili"
    	fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
    

    	// Costruisci l'objectName
    	String objectName = "immobili/" + fileName;
        
        // Recupera il blob dal bucket
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        if (blob == null) {
            throw new FileNotFoundException("Immagine non trovata: " + objectName);
        }
        
        // Legge i byte dell'immagine
        byte[] imageBytes = blob.getContent();
        
        // Converte i byte in una stringa Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }
}