package org.handnotes.model.responses;

public class LoginResponse implements IResponse {

    private String username;
    private String token;

    public LoginResponse(String email, String token) {
        this.username = email;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
