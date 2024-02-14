package org.handnotes.model.responses;

import org.handnotes.model.Note;

import java.util.List;

public class GetNotesResponse implements IResponse{

    List<Note> notes;

    public GetNotesResponse(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
