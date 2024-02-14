package org.handnotes.model.requests;

public class GetNotesRequest implements IRequest{

    private Integer page, size;

    public GetNotesRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
