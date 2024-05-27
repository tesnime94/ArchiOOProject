package com.example.Trip.Controleur;

import com.example.Trip.Models.NoteModel;
import com.example.Trip.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/send")
    public void sendNote(@RequestParam String emailFrom, @RequestParam String emailTo, @RequestBody NoteModel note) {
        noteService.sendNote(emailFrom, emailTo, note.getNote(), note.getCommentaire());
    }

    @GetMapping("/user/{email}")
    public List<NoteModel> getNotesByUser(@PathVariable String email) {
        return noteService.getNotesByEmail(email);
    }

    @GetMapping("/user/{email}/average")
    public double getAverageNoteForUser(@PathVariable String email) {
        return noteService.calculateAverageNoteForUser(email);
    }
}