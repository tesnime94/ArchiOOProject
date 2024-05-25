package com.example.Trip.Services;

import com.example.Trip.Models.VoyageModel;
import com.example.Trip.Repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    public List<VoyageModel> getAllVoyages() {
        return voyageRepository.findAll();
    }

    public VoyageModel getVoyageById(Integer id) {
        return voyageRepository.findById(id).orElse(null);
    }

    public VoyageModel saveVoyage(VoyageModel voyage) {
        return voyageRepository.save(voyage);
    }

    public VoyageModel updateVoyage(Integer id, VoyageModel updatedVoyage) {
        VoyageModel existingVoyage = voyageRepository.findById(id).orElse(null);

        if (existingVoyage != null) {
            existingVoyage.setName(updatedVoyage.getName());
            existingVoyage.setPassword(updatedVoyage.getPassword());
            return voyageRepository.save(existingVoyage);
        } else {
            return null;
        }
    }

    public void deleteVoyage(Integer id) {
        voyageRepository.deleteById(id);
    }
}
