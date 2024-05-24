package com.example.Trip.Repository;

import com.example.Trip.Models.AnnouncementModel;
import com.example.Trip.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<AnnouncementModel, Integer> {
    @Query("SELECT u FROM UserModel u JOIN u.announce r WHERE r.id = :announceId")
    List<UserModel> findUsersByAnnounceId(@Param("announceId") Integer announceId);
}

