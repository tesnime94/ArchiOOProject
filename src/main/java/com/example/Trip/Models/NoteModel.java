package com.example.Trip.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int note;
    private String commentaire;
    private String email;

    @ManyToOne
    @JoinColumn(name = "voyage_id")
    private VoyageModel voyage;
}
