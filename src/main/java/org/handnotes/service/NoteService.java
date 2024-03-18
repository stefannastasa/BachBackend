package org.handnotes.service;


import org.handnotes.cloud.S3Plugin;
import org.handnotes.model.Note;
import org.handnotes.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final S3Plugin s3Plugin;

    public NoteService(NoteRepository noteRepository, S3Plugin s3Plugin) {
        this.noteRepository = noteRepository;
        this.s3Plugin = s3Plugin;
    }

    public List<Note> findAllByUser(String userId){
        return noteRepository.findNotesByUserId(userId);
    }

    public void saveNote(Note note){
        noteRepository.save(note);
    }

    private List<String> getSigned(List<String> keys){
        List<String> signedUrls = new ArrayList<>();
        for(String photoKey: keys){
            String signedUrl = s3Plugin.createSignedUrl(photoKey);
            signedUrls.add(signedUrl);
        }

        return signedUrls;
    }

    private Page<Note> convertToSignedUrls(Page<Note> toSend){
        for (Note note: toSend){
            List<String> signedUrls = getSigned(note.getImageUrls());
            List<String> thumb_signedUrls = getSigned(note.getThumbnailUrls());

            note.setImageUrls(signedUrls);
            note.setThumbnailUrls(thumb_signedUrls);
        }
        return toSend;
    }

    public Page<Note> findPageByUser(String userId, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        if(page * size > noteRepository.countNotesByUserId(userId))
            throw new Exception("Out of scope request.");

        return convertToSignedUrls(noteRepository.findNotesByUserId(userId, pageable));
    }

    public Note findNoteById(String _id){

        return noteRepository.findById(_id).orElse(null);
    }

    public Page<Note> titleFilteredPageByUser(String userId, String searchString, int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        if(page * size > noteRepository.countNotesByUserIdAndTitleContaining(userId, searchString))
            throw new Exception("Out of scope request.");

        return convertToSignedUrls(noteRepository.findNotesByUserIdAndTitleContaining(userId, searchString, pageable));
    }

    public void uploadImage(String id, MultipartFile file){

        Note note = noteRepository.findNoteById(id);

        String fileKey = s3Plugin.uploadImages(file);

        note.getImageUrls().add(fileKey);
        note.getThumbnailUrls().add("SMALL_"+fileKey);
        noteRepository.save(note);

    }

}
