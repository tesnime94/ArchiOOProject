package com.example.Trip.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ElementCollection
    private List<Integer> reports;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_announce",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "announce_id") }
    )
    private List<AnnouncementModel> announce;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteModel> notes;
}
