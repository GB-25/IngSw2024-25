package server.business_logic_layer;

import server.data_access_layer.interfaces.StorageManagerInterface;

import java.io.IOException;

public class GoogleCloudStorageService {
	
	private StorageManagerInterface storageManager;
	/**
     * 
     * Costruttore
     */
    public GoogleCloudStorageService(StorageManagerInterface storageManager) {
        this.storageManager = storageManager;
    }

    

    public String uploadHouseImage(String fileName, String base64Data, int idCartella) throws IOException {
        return storageManager.uploadFile(fileName, base64Data, idCartella);
    }
    
    
   
    public String downloadHouseImage(String fileName) throws IOException {
        return storageManager.downloadImageAsBase64(fileName);
    }
}

