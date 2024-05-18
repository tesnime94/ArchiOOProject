package com.example.Trip.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementModel {
    private Integer id;
    private String title;
    private String description;
    private String period;
    private Double budget;
    @Lob
    private byte[] image;

    @ManyToMany(mappedBy = "announce", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserModel> users = new ArrayList<>();


}
