package org.handnotes.model.responses;

import lombok.Getter;
import lombok.Setter;
import org.handnotes.model.Note;

import java.util.List;

@Setter
@Getter
public class GetNotesResponse implements IResponse{

    List<Note> notes;
    public GetNotesResponse(List<Note> notes) {
        this.notes = notes;
    }

}
