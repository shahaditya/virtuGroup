package com.thevirtugroup.postitnote.rest;

import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import com.thevirtugroup.postitnote.repository.NotesRepository;
import com.thevirtugroup.postitnote.repository.UserRepository;
import com.thevirtugroup.postitnote.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class NoteController {


    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/getAllNotes", method = RequestMethod.GET, headers = "Accept=application/json")
    public Notes getNotes(Model model) {
        return notesRepository.getNotes(SecurityContext.getLoggedInUser().getId());
    }

    @RequestMapping(value = "/addNote", method = RequestMethod.POST, headers = "Accept=application/json")
    public Notes insertNotes(@RequestBody Note note) {
        notesRepository.insertNote(SecurityContext.getLoggedInUser().getId(), note);
        return notesRepository.getNotes(SecurityContext.getLoggedInUser().getId());
    }

}
