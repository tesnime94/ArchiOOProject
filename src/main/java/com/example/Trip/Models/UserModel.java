package com.example.Trip.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String birth;
    private String password;
    private String address;
    private Integer phoneNumber;
    @Column(unique = true)
    private String email;

    @ManyToMany(cascade = { CascadeType.ALL }) //relation entre deux tables on a une table d'association
    @JoinTable( //table d'association
            name = "user_announce",
            joinColumns = { @JoinColumn(name = "user_id") }, //on ajoute 2 champs a la table d'association
            inverseJoinColumns = { @JoinColumn(name = "announce_id") }
    )
    @JsonIgnore
    private List<AnnouncementModel> announce = new ArrayList<>();


}