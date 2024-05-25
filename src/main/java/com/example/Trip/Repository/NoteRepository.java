package com.example.Trip.Repository;

import com.example.Trip.Models.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteModel, Integer> {

    List<NoteModel> findByEmail(String email);

    List<NoteModel> findByVoyageId(Integer voyageId);
}
