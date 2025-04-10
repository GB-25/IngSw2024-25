package server.data_access_layer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import server.data_access_layer.interfaces.StorageManagerInterface;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Base64;
import java.io.FileNotFoundException;

public class GoogleCloudStorageManager implements StorageManagerInterface{
    private static final String BUCKET_NAME = "foto-ingsw-2024-25";
    private static final String CREDENTIALS_PATH = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
    private final Storage storage;

	/**
     * 
     * Costruttore
     */
    public GoogleCloudStorageManager() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(CREDENTIALS_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    
    @Override
    public String uploadFile(String fileName, String base64Data, int idCartella) throws IOException {
        byte[] fileBytes = Base64.getDecoder().decode(base64Data);

        String objectName = "immobili/cartella"+ idCartella+ "/"+ fileName;
      
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();

        storage.create(blobInfo, fileBytes);
        

        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + objectName;
    }
    
    
    @Override
    public String downloadImageAsBase64(String fileName) throws IOException {

    	String[] parts = fileName.split("/");
        String folderName = parts[parts.length - 2];
        String file = parts[parts.length - 1];

    	String objectName = "immobili/" + folderName + "/" + file;

        Blob blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        if (blob == null) {
            throw new FileNotFoundException("Immagine non trovata: " + objectName);
        }

        byte[] imageBytes = blob.getContent();

        return Base64.getEncoder().encodeToString(imageBytes);
       
    }
}
