package com.wellsfargo.hackathon.pronunciation.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UploadObjectFromMemory {

    public static final String PROJECT_ID = "";
    public static final String BUCKET_NAME = "";

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

        Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] content = file.getBytes();
//        byte[] content = contents.getBytes(StandardCharsets.UTF_8);
        storage.createFrom(blobInfo, new ByteArrayInputStream(content));

        System.out.println(
                "Object "
                        + objectName
                        + " uploaded to bucket "
                        + BUCKET_NAME
                        + " with contents "
                        + content);
    }

    public static void uploadObjectFromMemory(
            String projectId, String bucketName, String objectName, String contents) throws IOException {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The string of contents you wish to upload
        // String contents = "Hello world!";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] content = contents.getBytes(StandardCharsets.UTF_8);
        storage.createFrom(blobInfo, new ByteArrayInputStream(content));

        System.out.println(
                "Object "
                        + objectName
                        + " uploaded to bucket "
                        + bucketName
                        + " with contents "
                        + contents);
    }
}
