package com.example.Trip.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String period;
    private double budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;  // Lien vers l'utilisateur qui a posté l'annonce

    @ManyToMany(mappedBy = "announce")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<UserModel> favoritedByUsers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voyage_id")  // Colonne pour stocker l'ID du voyage associé
    private VoyageModel voyage;
}
