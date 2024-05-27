package com.example.Trip.Services;

import com.example.Trip.Models.PurchaseModel;
import com.example.Trip.Models.UserModel;
import com.example.Trip.Models.VoyageModel;
import com.example.Trip.Repository.PurchaseRepository;
import com.example.Trip.Repository.UserRepository;
import com.example.Trip.Repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoyageRepository voyageRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private VoyageService voyageService;

    @Transactional
    public PurchaseModel addPurchase(Integer userId, Integer voyageId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        VoyageModel voyage = voyageRepository.findById(voyageId)
                .orElseThrow(() -> new RuntimeException("Voyage not found: " + voyageId));

        PurchaseModel purchase = new PurchaseModel();
        purchase.setUser(user);
        purchase.setVoyage(voyage);
        purchase.setTotalPrices(voyage.getPrice());
        purchase.setPaymentComplete(false);

        return purchaseRepository.save(purchase);
    }

    public boolean checkUserEligibilityForDiscount( int userId) {

        int paymentCount = purchaseRepository.countPaymentsByUserId( userId);
        return paymentCount > 10;
    }

    @Transactional
    public boolean validateCart(Integer userId) {
        List<PurchaseModel> purchases = purchaseRepository.findAllByUserIdAndIsPaymentComplete(userId, false);
        if (purchases.isEmpty()) {
            return false;
        }

        boolean isEligibleForDiscount = checkUserEligibilityForDiscount(userId);

        for (PurchaseModel purchase : purchases) {
            VoyageModel voyage = purchase.getVoyage();
            if (!voyageService.checkVoyageAvailability(voyage)) {
                return false;
            }

            double price = voyage.getPrice();
            if (isEligibleForDiscount) {
                double discount = price * 0.05; // 5% discount
                price -= discount;
            }

            purchase.setTotalPrices(price);
            purchase.setPaymentComplete(true);
        }

        purchaseRepository.saveAll(purchases);
        return true;
    }
    public boolean deletePurchase(Integer purchaseId) {
        if (!purchaseRepository.existsById(purchaseId)) {
            return false;
        }
        purchaseRepository.deleteById(purchaseId);
        return true;
    }
}
