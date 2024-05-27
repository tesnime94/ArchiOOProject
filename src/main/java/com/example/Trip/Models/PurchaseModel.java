package com.example.Trip.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PurchaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "voyage_id")

    private VoyageModel voyage;

    private double totalPrices;
    private boolean isPaymentComplete;

    private String promoCode;
}
