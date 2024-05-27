package com.example.Trip.Repository;

import com.example.Trip.Models.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseModel, Integer> {
    @Query("SELECT p FROM PurchaseModel p WHERE p.user.id = :userId AND p.isPaymentComplete = :isComplete")
    List<PurchaseModel> findAllByUserIdAndIsPaymentComplete(@Param("userId") Integer userId, @Param("isComplete") boolean isPaymentComplete);

    @Query("SELECT COUNT(p) FROM PurchaseModel p WHERE p.user.id = :userId AND p.isPaymentComplete = true")
    int countPaymentsByUserId(@Param("userId") int userId);
}
