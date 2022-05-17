package com.wellsfargo.hackathon.pronunciation.domain;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "pronunciation_record")
public class PronunciationRecord {

    @Id
    Long id;

    String userName;

    String pronunciationBucketFileName;

    String dateMMddyyyy;

    public PronunciationRecord(String userName, String pronunciationBucketFileNmae, String dateMMddyyyy) {
        this.userName = userName;
        this.pronunciationBucketFileName = pronunciationBucketFileNmae;
        this.dateMMddyyyy = dateMMddyyyy;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Pronunciation{" +
                "id=" + this.id +
                ", userName='" + this.userName + '\'' +
                ", pronunciation='" + this.pronunciationBucketFileName + '\'' +
                ", date=" + this.dateMMddyyyy +
                '}';
    }
}
