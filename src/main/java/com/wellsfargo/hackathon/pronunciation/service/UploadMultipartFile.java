package com.wellsfargo.hackathon.pronunciation.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.wellsfargo.hackathon.pronunciation.PronunciationConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class UploadMultipartFile {


    // get service by env var GOOGLE_APPLICATION_CREDENTIALS. Json file generated in API & Services -> Service account key
    private static Storage storage = StorageOptions.getDefaultInstance().getService();

    public static String upload(MultipartFile file) throws IOException {
        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(PronunciationConstants.BUCKET_NAME, file.getOriginalFilename()).build(), //get original file name
//                    BlobInfo.newBuilder("[Bucket_name]", file.getOriginalFilename()).build(), //get original file name
                    file.getBytes(), // the file
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ) // Set file permission
            );
            return blobInfo.getMediaLink(); // Return file url
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }


    public static void uploadObjectFromMultipartFile(
            /*String projectId, String bucketName, */String objectName, MultipartFile file) throws IOException {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The string of contents you wish to upload
        // String contents = "Hello world!";

        Storage storage = StorageOptions.newBuilder().setProjectId(PronunciationConstants.PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(PronunciationConstants.BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] content = file.getBytes();
//        byte[] content = contents.getBytes(StandardCharsets.UTF_8);
        storage.createFrom(blobInfo, new ByteArrayInputStream(content));

        System.out.println(
                "Object "
                        + objectName
                        + " uploaded to bucket "
                        + PronunciationConstants.BUCKET_NAME
                        + " with contents "
                        + content);
    }
}
