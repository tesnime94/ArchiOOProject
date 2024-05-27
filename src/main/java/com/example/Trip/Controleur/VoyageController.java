package com.example.Trip.Controleur;

import com.example.Trip.Models.VoyageModel;
import com.example.Trip.Services.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voyages")
public class VoyageController {

    @Autowired
    private VoyageService voyageService;

    @GetMapping()
    public List<VoyageModel> getAllVoyages() {
        return voyageService.getAllVoyages();
    }

    @GetMapping("/{id}")
    public VoyageModel getVoyageById(@PathVariable Integer id) {
        return voyageService.getVoyageById(id);
    }

    @PostMapping()
    public VoyageModel saveVoyage(@RequestBody VoyageModel voyage) {
        return voyageService.saveVoyage(voyage);
    }


    @PutMapping("/{id}")
    public VoyageModel updateVoyage(@PathVariable Integer id, @RequestBody VoyageModel updatedVoyage) {
        return voyageService.updateVoyage(id, updatedVoyage);
    }

    @DeleteMapping("/{id}")
    public void deleteVoyage(@PathVariable Integer id) {
        voyageService.deleteVoyage(id);
    }
}
