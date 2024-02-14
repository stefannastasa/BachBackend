package org.handnotes.model.responses;

public class DeauthResponse implements IResponse{

    private String message;

    public DeauthResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
