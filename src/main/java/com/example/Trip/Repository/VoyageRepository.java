package com.example.Trip.Repository;

import com.example.Trip.Models.VoyageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageRepository extends JpaRepository<VoyageModel, Integer> {

}
