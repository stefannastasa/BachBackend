package org.handnotes.model.responses;

import org.handnotes.model.Note;

import java.util.List;

public class CreateNoteReponse implements IResponse{

    private String message;
    private List<String> uploadUrls;

    private Note createdNote;

    public CreateNoteReponse(String message, List<String> uploadUrls, Note createdNote) {
        this.message = message;
        this.uploadUrls = uploadUrls;
        this.createdNote = createdNote;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getUploadUrls() {
        return uploadUrls;
    }

    public void setUploadUrls(List<String> uploadUrls) {
        this.uploadUrls = uploadUrls;
    }

    public Note getCreatedNote() {
        return createdNote;
    }

    public void setCreatedNote(Note createdNote) {
        this.createdNote = createdNote;
    }
}
