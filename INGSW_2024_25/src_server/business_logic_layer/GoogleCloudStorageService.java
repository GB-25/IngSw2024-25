package business_logic_layer;

import data_access_layer.interfaces.StorageManagerInterface;

import java.io.IOException;

public class GoogleCloudStorageService {
	
	private StorageManagerInterface storageManager;
    

    public GoogleCloudStorageService(StorageManagerInterface storageManager) {
        this.storageManager = storageManager;
    }

    

    public String uploadHouseImage(String fileName, String base64Data, int idCartella) throws IOException {
        // Qui puoi aggiungere logica di validazione (tipo file, dimensione, ecc.)
        return storageManager.uploadFile(fileName, base64Data, idCartella);
    }
    
    
   
    public String downloadHouseImage(String fileName) throws IOException {
        // Eventuali controlli o logiche di validazione possono essere inseriti qui
        return storageManager.downloadImageAsBase64(fileName);
    }
}

