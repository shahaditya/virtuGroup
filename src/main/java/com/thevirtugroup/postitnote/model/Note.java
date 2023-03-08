package com.thevirtugroup.postitnote.model;

import java.time.Instant;
import java.util.Objects;

public class Note {

    private Long noteId;
    private String name;
    private String summary;

    private Instant updatedTime;

    public Note() {
    }
    public Note(Long noteId, String name, String summary, Instant updatedTime) {
        this.noteId = noteId;
        this.name = name;
        this.summary = summary;
        this.updatedTime = updatedTime;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return noteId.equals(note.noteId) && name.equals(note.name) && summary.equals(note.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, name, summary);
    }
}
