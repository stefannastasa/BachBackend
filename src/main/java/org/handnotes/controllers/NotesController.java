package org.handnotes.controllers;



import org.handnotes.auth.AuthenticationService;
import org.handnotes.model.Note;
import org.handnotes.model.User;
import org.handnotes.model.requests.CreateNoteRequest;
import org.handnotes.model.requests.ModifyNoteRequest;
import org.handnotes.model.responses.*;
import org.handnotes.service.NoteService;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NoteService noteService;
    private final AuthenticationService authenticationService;
    public NotesController(NoteService noteService, AuthenticationService authenticationService) {
        this.noteService = noteService;
        this.authenticationService = authenticationService;
    }

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<IResponse> getNotes(@RequestParam("page") String page, @RequestParam("size") String size){
        User authenticatedUser = authenticationService.retrieveLoggedInUser();

        try{
            int pg = Integer.parseInt(page);
            int sz = Integer.parseInt(size);
            Page<Note> notes = noteService.findPageByUser(authenticatedUser.getId(), pg, sz);

            return ResponseEntity.ok(new GetNotesResponse(notes.getContent()));
        }catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteNote(@RequestParam("id") String noteId){
        try{
            noteService.deleteNote(noteId);
            return new ResponseEntity<>("Deleted note.", HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<>("Deletion failed because" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPage(@RequestParam("id") String id, @RequestPart(name = "file") MultipartFile file){
        System.out.println("Uploading file...");
        if(file.isEmpty()){
            return new ResponseEntity<>("Invalid photo", HttpStatus.BAD_REQUEST);
        }
        try{
            noteService.uploadImage(id, file);
            return new ResponseEntity<>("Upload successful.", HttpStatus.ACCEPTED);

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Upload failed because" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/upload", method= RequestMethod.PUT)
    public ResponseEntity<String> uploadDone(@RequestParam("id") String noteId){
        System.out.println("Upload of pages is done.");
        User authenticatedUser = authenticationService.retrieveLoggedInUser();

        try{
            noteService.uploadDone(noteId, authenticatedUser.getUsername());
            return new ResponseEntity<>("Upload registered.", HttpStatus.ACCEPTED);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Upload not registered" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<IResponse> createNote(@RequestBody CreateNoteRequest creatRequest){
        User authenticatedUser = authenticationService.retrieveLoggedInUser();
        System.out.println("Creating note...");
        try{
            String title = creatRequest.getTitle();
            Note note = new Note(authenticatedUser.getId(), title, "");
            noteService.saveNote(note);

            return ResponseEntity.ok(new CreateNoteReponse("Note created.", note));
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method=RequestMethod.PUT)
    public ResponseEntity<IResponse> modifyNote(@RequestParam("id") String id, @RequestBody ModifyNoteRequest modifyRequest){
        String finId = id.replace("\"", "");
        Note note = noteService.findNoteById(finId);

        if(note != null){
            if(!modifyRequest.getTitle().isEmpty()){
                note.setTitle(modifyRequest.getTitle());
            }

            if(!modifyRequest.getFilePath().isEmpty()) {
                note.setImageUrls(modifyRequest.getFilePath());
            }

            if(!modifyRequest.getContent().isEmpty()){
                note.setContent(modifyRequest.getContent());
            }
            noteService.saveNote(note);
            return ResponseEntity.ok(new ModifyNoteResponse("Note modified."));
        }

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "No note found with given id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ResponseBody
    @RequestMapping(value="/filter", method=RequestMethod.GET)
    public ResponseEntity<IResponse> filterNotes(@RequestParam("title") String title, @RequestParam("page") String page, @RequestParam("size") String size){
        User authenticatedUser = authenticationService.retrieveLoggedInUser();

        try{
            int pg = Integer.parseInt(page);
            int sz = Integer.parseInt(size);
            Page<Note> notes = noteService.titleFilteredPageByUser(authenticatedUser.getId(), title, pg, sz);

            return ResponseEntity.ok(new GetNotesResponse(notes.getContent()));
        }catch(Exception e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

}
