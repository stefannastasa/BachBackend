package org.handnotes.model.requests;

import java.util.List;

public class CreateNoteRequest implements IRequest{
    private String title;
    private Integer number_of_files;

    public CreateNoteRequest(String title, Integer number_of_files) {
        this.title = title;
        this.number_of_files = number_of_files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
