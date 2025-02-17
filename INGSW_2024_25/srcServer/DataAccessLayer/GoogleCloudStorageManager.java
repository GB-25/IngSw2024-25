package DataAccessLayer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoogleCloudStorageManager {
    private static final String BUCKET_NAME = "foto-ingsw-2024-25";
    private static final String CREDENTIALS_PATH = "/INGSW_2024_25/scientific-base-449814-j0-e3a3cf2780c9.json";
    private final Storage storage;

    public GoogleCloudStorageManager() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(CREDENTIALS_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public String uploadFile(String filePath) throws IOException {
        String objectName = "immobili/" + Paths.get(filePath).getFileName();

        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + objectName;
    }
}