package org.handnotes.model.requests;

public class LogoutRequest implements IRequest{

    private String username;

    public LogoutRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
