package com.example.Trip.Services;

import com.example.Trip.Models.NoteModel;
import com.example.Trip.Models.UserModel;
import com.example.Trip.Models.VoyageModel;
import com.example.Trip.Repository.NoteRepository;
import com.example.Trip.Repository.UserRepository;
import com.example.Trip.Repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    public NoteModel saveNote(NoteModel note) {
        return noteRepository.save(note);
    }

    public List<NoteModel> getNotesByEmail(String email) {
        return noteRepository.findByEmail(email);
    }

    public boolean haveTraveledTogether(String email1, String email2) {
        List<VoyageModel> voyages1 = voyageRepository.findByUsersEmail(email1);
        List<VoyageModel> voyages2 = voyageRepository.findByUsersEmail(email2);
        return voyages1.stream().anyMatch(voyages2::contains);
    }

    public void sendNote(String emailFrom, String emailTo, int noteValue, String commentaire) {
        if (noteValue < 1 || noteValue > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5");
        }

        if (!haveTraveledTogether(emailFrom, emailTo)) {
            throw new IllegalArgumentException("Les utilisateurs doivent avoir voyagé ensemble");
        }

        NoteModel note = new NoteModel();
        note.setEmail(emailTo);
        note.setNote(noteValue);
        note.setCommentaire(commentaire);

        note.setVoyage(getCommonVoyage(emailFrom, emailTo));

        noteRepository.save(note);
    }

    private VoyageModel getCommonVoyage(String email1, String email2) {

        List<VoyageModel> voyages1 = voyageRepository.findByUsersEmail(email1);
        List<VoyageModel> voyages2 = voyageRepository.findByUsersEmail(email2);
        return voyages1.stream().filter(voyages2::contains).findFirst().orElse(null);
    }

    public double calculateAverageNoteForUser(String email) {
        List<NoteModel> notes = noteRepository.findByEmail(email);
        OptionalDouble average = notes.stream().mapToInt(NoteModel::getNote).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }
}
