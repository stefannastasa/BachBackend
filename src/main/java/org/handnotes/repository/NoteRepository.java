package org.handnotes.repository;

import org.apache.el.stream.Optional;
import org.handnotes.model.User;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.handnotes.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findNotesByUserId(String userId);
    Integer countNotesByUserId(String userId);

    Page<Note> findNotesByUserId(String userId, Pageable pageable);


    Page<Note> findNotesByUserIdAndTitleContaining(String userId, String titleContaining, Pageable pageable);
    Integer countNotesByUserIdAndTitleContaining(String userId, String titleContaining);

    Note findNoteById(String id);

}
