package com.example.Trip.Controleur;

import com.example.Trip.Models.PurchaseModel;
import com.example.Trip.Services.PurchaseService;
import com.example.Trip.Dto.PurchaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/add")
    public ResponseEntity<PurchaseModel> addPurchase(@RequestBody PurchaseDto purchaseDto) {
        try {
            PurchaseModel purchase = purchaseService.addPurchase(
                    purchaseDto.getUserId(),
                    purchaseDto.getVoyageId()
            );
            return new ResponseEntity<>(purchase, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<String> validateCart(@PathVariable Integer userId) {
        boolean result = purchaseService.validateCart(userId);
        if (result) {
            return ResponseEntity.ok("Cart validated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to validate cart.");
        }
    }

    @DeleteMapping("/delete/{purchaseId}")
    public ResponseEntity<String> deletePurchase(@PathVariable Integer purchaseId) {
        boolean result = purchaseService.deletePurchase(purchaseId);
        if (result) {
            return ResponseEntity.ok("Purchase deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
