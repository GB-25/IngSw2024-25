package data_access_layer.interfaces;

import java.io.IOException;

public interface StorageManagerInterface {

	String uploadFile(String fileName, String base64Data, int idCartella) throws IOException;
	
	String downloadImageAsBase64(String fileName) throws IOException;
}
