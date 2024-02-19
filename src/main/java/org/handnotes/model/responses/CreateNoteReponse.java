package org.handnotes.model.responses;

import org.handnotes.model.Note;

import java.util.List;

public class CreateNoteReponse implements IResponse{

    private String message;

    private Note createdNote;

    public CreateNoteReponse(String message, Note createdNote) {
        this.message = message;
        this.createdNote = createdNote;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Note getCreatedNote() {
        return createdNote;
    }

    public void setCreatedNote(Note createdNote) {
        this.createdNote = createdNote;
    }
}
