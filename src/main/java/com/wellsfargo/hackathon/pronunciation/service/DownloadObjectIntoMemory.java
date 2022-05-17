package com.wellsfargo.hackathon.pronunciation.service;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class DownloadObjectIntoMemory {
    public static byte[] downloadObjectIntoMemory(
            String projectId, String bucketName, String objectName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        byte[] content = storage.readAllBytes(bucketName, objectName);
        System.out.println(
                "The contents of "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " are: ");
        //                        + new String(content, StandardCharsets.UTF_8))


        return content;
    }
}
