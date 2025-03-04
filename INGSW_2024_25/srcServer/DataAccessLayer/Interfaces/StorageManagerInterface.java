package DataAccessLayer.Interfaces;

import java.io.IOException;

public interface StorageManagerInterface {

	String uploadFile(String fileName, String base64Data) throws IOException;
	
	String downloadImageAsBase64(String fileName) throws IOException;
}
