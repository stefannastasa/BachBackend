package org.handnotes.service;


import org.handnotes.model.Note;
import org.handnotes.model.User;
import org.handnotes.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAllByUser(String userId){
        return noteRepository.findNotesByUserId(userId);
    }

    public void saveNote(Note note){
        noteRepository.save(note);
    }

    public Page<Note> findPageByUser(String userId, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        if(page * size > noteRepository.countNotesByUserId(userId))
            throw new Exception("Out of scope request.");
        return noteRepository.findNotesByUserId(userId, pageable);
    }

    public Note findNoteById(String _id){

        return noteRepository.findById(_id).orElse(null);
    }

    public Page<Note> titleFilteredPageByUser(String userId, String searchString, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        if(page * size > noteRepository.countNotesByUserIdAndTitleContaining(userId, searchString))
            throw new Exception("Out of scope request.");

        return noteRepository.findNotesByUserIdAndTitleContaining(userId, searchString, pageable);

    }

}
