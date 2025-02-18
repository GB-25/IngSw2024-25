package BusinessLogicLayer;

import DataAccessLayer.GoogleCloudStorageManager;
import java.io.IOException;

public class GoogleCloudStorageService {
    private final GoogleCloudStorageManager storageManager;

    public GoogleCloudStorageService() throws IOException {
        this.storageManager = new GoogleCloudStorageManager();
    }

    public String uploadHouseImage(String fileName, String base64Data) throws IOException {
        // Qui puoi aggiungere logica di validazione (tipo file, dimensione, ecc.)
        return storageManager.uploadFile(fileName, base64Data);
    }
    
    public String downloadHouseImage(String fileName) throws IOException {
        // Eventuali controlli o logiche di validazione possono essere inseriti qui
        return storageManager.downloadImageAsBase64(fileName);
    }
}

