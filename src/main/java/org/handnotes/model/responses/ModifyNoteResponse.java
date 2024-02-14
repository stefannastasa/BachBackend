package org.handnotes.model.responses;

public class ModifyNoteResponse implements IResponse{

    private String message;

    public ModifyNoteResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
