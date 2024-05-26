package com.example.Trip.Services;

import com.example.Trip.Models.NoteModel;
import com.example.Trip.Models.UserModel;
import com.example.Trip.Models.VoyageModel;
import com.example.Trip.Repository.NoteRepository;
import com.example.Trip.Repository.UserRepository;
import com.example.Trip.Repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<VoyageModel> voyages1 = new HashSet<>(getVoyagesByEmail(email1));
        Set<VoyageModel> voyages2 = new HashSet<>(getVoyagesByEmail(email2));
        voyages1.retainAll(voyages2);
        return !voyages1.isEmpty();
    }

    private List<VoyageModel> getVoyagesByEmail(String email) {
        UserModel user = userRepository.getUserByEmail(email);
        if (user != null) {
            return user.getVoyages();
        }
        return List.of(); // Return an empty list if the user does not exist
    }

    public void sendNote(String emailFrom, String emailTo, int noteValue, String commentaire) {
        if (noteValue < 1 || noteValue > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5");
        }

        if (!haveTraveledTogether(emailFrom, emailTo)) {
            throw new IllegalArgumentException("Les utilisateurs doivent avoir voyagé ensemble");
        }

        UserModel userFrom = userRepository.getUserByEmail(emailFrom);
        UserModel userTo = userRepository.getUserByEmail(emailTo);

        if (userTo == null) {
            throw new IllegalArgumentException("L'utilisateur destinataire n'existe pas");
        }

        NoteModel note = new NoteModel();
        note.setEmail(emailTo);
        note.setNote(noteValue);
        note.setCommentaire(commentaire);
        note.setUser(userTo);
        note.setVoyage(getCommonVoyage(emailFrom, emailTo));

        noteRepository.save(note);
    }

    private VoyageModel getCommonVoyage(String email1, String email2) {
        Set<VoyageModel> voyages1 = new HashSet<>(getVoyagesByEmail(email1));
        Set<VoyageModel> voyages2 = new HashSet<>(getVoyagesByEmail(email2));
        voyages1.retainAll(voyages2);
        return voyages1.stream().findFirst().orElse(null);
    }

    public double calculateAverageNoteForUser(String email) {
        List<NoteModel> notes = noteRepository.findByEmail(email);
        OptionalDouble average = notes.stream().mapToInt(NoteModel::getNote).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }
}