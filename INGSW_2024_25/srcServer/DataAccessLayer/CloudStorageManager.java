package DataAccessLayer;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.storage.*;
import java.io.*;

public class CloudStorageManager {
    private static final String BUCKET_NAME = "mio-bucket-storage";
    //private Storage storage;

    public CloudStorageManager() {
//        try {
//            storage = StorageOptions.newBuilder()
//                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("path/alle/credenziali.json")))
//                    .build()
//                    .getService();
//            System.out.println("✅ Connessione a Google Cloud Storage riuscita!");
//        } catch (IOException e) {
//            System.err.println("❌ Errore nella connessione a Cloud Storage!");
//            e.printStackTrace();
//        }
    }

    public void uploadFile(String filePath, String destFileName) {
//        try {
//            File file = new File(filePath);
//            BlobId blobId = BlobId.of(BUCKET_NAME, destFileName);
//            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//            storage.create(blobInfo, new FileInputStream(file));
//            System.out.println("✅ File caricato: " + destFileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
