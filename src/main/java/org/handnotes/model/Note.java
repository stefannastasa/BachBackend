package org.handnotes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    private final String userId;
    private String title;
    private String content;
    private List<String> imageUrls;
    private Date createdAt;
    private Date updatedAt;

    private Boolean transcribed = false;
    public Note(String userId, String title, String content, List<String> imageUrls) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.id = generateUid();
    }

    private String generateUid(){
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        setUpdatedAt(new Date());
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        setUpdatedAt(new Date());
        this.content = content;
    }

    public List<String> getImageUrl() {
        return imageUrls;
    }

    public void setImageUrl(List<String> imageUrls) {
        setUpdatedAt(new Date());
        this.imageUrls = imageUrls;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    private void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
