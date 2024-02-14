package org.handnotes.model.requests;

import java.util.List;

public class ModifyNoteRequest implements IRequest{

    private String title;
    private List<String> filePaths;
    private String content;
    public ModifyNoteRequest(String title, List<String> filePaths, String content) {
        this.title = title;
        this.filePaths = filePaths;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getFilePath() {
        return filePaths;
    }

    public void setFilePath(List<String> filePath) {
        this.filePaths = filePaths;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
