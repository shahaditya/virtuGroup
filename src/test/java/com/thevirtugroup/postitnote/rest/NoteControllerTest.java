package com.thevirtugroup.postitnote.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thevirtugroup.postitnote.model.Note;
import com.thevirtugroup.postitnote.model.Notes;
import com.thevirtugroup.postitnote.security.SecurityUserWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @WithMockUser
    @Test
    public void TestGetAllNotesReturnsNotesWhenIsMatched() throws Exception {

        SecurityUserWrapper userWrapper = new SecurityUserWrapper("user", "password", AuthorityUtils.createAuthorityList());
        userWrapper.setId(999L);
        MvcResult result = mockMvc.perform(get("/getAllNotes").with(user(userWrapper)))
                .andExpect(status().
                        isOk()).andReturn();
        Notes atualNotes = objectMapper.readValue(result.getResponse().getContentAsString(), Notes.class);
        Assert.assertEquals(getExpectedNotes(), atualNotes);

    }

    private Notes getExpectedNotes() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(1L, "Note1", "This is a test note", Instant.now()));
        return new Notes(noteList);
    }
}
