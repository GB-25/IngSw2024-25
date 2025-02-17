package BusinessLogicLayer;

import DataAccessLayer.GoogleCloudStorageManager;
import java.io.IOException;

public class GoogleCloudStorageService {
    private final GoogleCloudStorageManager storageManager;

    public GoogleCloudStorageService() throws IOException {
        this.storageManager = new GoogleCloudStorageManager();
    }

    public String uploadUserImage(String filePath) throws IOException {
        // Eventuale logica di validazione (es. verificare tipo di file, dimensione, etc.)
        return storageManager.uploadFile(filePath);
    }
}

