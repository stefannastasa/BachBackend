package org.handnotes.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "notes")
public class Note {
    @Getter
    @Id
    private String id;

    @Getter
    private final String userId;
    @Getter
    private String title;
    @Getter
    private String content;
    @Getter
    private List<String> imageUrls = new ArrayList<>();

    @Getter
    @Setter
    private List<String> thumbnailUrls = new ArrayList<>();

    @Getter
    private Date createdAt;
    @Getter
    private Date updatedAt;

    @Getter
    @Setter
    private Boolean transcribed = false;

    public Note(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.id = generateUid();

    }

    private String generateUid(){
        return UUID.randomUUID().toString();
    }

    public void setTitle(String title) {
        setUpdatedAt(new Date());
        this.title = title;
    }

    public void setContent(String content) {
        setUpdatedAt(new Date());
        this.content = content;
    }

    public void setImageUrls(List<String> imageUrls) {
        setUpdatedAt(new Date());
        this.imageUrls = imageUrls;
    }

    private void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
