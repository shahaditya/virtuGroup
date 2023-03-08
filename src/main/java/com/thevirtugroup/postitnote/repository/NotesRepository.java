package com.thevirtugroup.postitnote.repository;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;

@Repository
public class NotesRepository {
    private static Map<Long, Notes> notes = new HashMap<>();

    static {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(1L, "Note1", "This is a test note", Instant.now()));
        notes.put(999L, new Notes(noteList));
    }

    public void insertNote(Long userId, Note note) {
        note.setNoteId(new Random().nextLong());
        note.setUpdatedTime(Instant.now());
        if (notes.containsKey(userId)) {
            notes.get(userId).getNotes().add(note);
        } else {
            notes.put(userId, new Notes(Arrays.asList(note)));
        }
    }

    public void updateNote(Long userId, Note note) {
        if (notes.containsKey(userId)) {
            List<Note> existingNotes = notes.get(userId).getNotes();
            Optional<Note> findNote = existingNotes.stream().filter(e -> e.getNoteId().equals(note.getNoteId())).findFirst();
            if (findNote.isPresent()) {
                findNote.get().setSummary(note.getSummary());
                findNote.get().setUpdatedTime(Instant.now());
                return;
            }
        }
        insertNote(userId, note);
    }

    public Notes getNotes(Long userId) {
        return notes.get(userId);
    }


}
