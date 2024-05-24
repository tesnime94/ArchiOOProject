package com.example.Trip.Services;

import com.example.Trip.Models.AnnouncementModel;
import com.example.Trip.Models.UserModel;
import com.example.Trip.Repository.AnnouncementRepository;
import com.example.Trip.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public AnnouncementModel addAnnouncement(AnnouncementModel announcement) {
        return announcementRepository.save(announcement);
    }

    @Transactional
    public void addFavoriteAnnouncement(Integer userId, Integer announcementId) {
        Optional<UserModel> user = userRepository.findById(userId);
        Optional<AnnouncementModel> announcement = announcementRepository.findById(announcementId);
        if (user.isPresent() && announcement.isPresent()) {
            user.get().getAnnounce().add(announcement.get());
            userRepository.save(user.get());
        }
    }

    public void deleteAnnouncement(Integer id) {
        announcementRepository.deleteById(id);
    }

    @Transactional
    public void reportAnnouncement(Integer id) {
        Optional<AnnouncementModel> announcement = announcementRepository.findById(id);
        if (announcement.isPresent()) {
            // Logique pour signaler une annonce, pas encore fait
        }
    }
}