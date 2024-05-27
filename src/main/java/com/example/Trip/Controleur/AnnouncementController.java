package com.example.Trip.Controleur;

import com.example.Trip.Models.AnnouncementModel;
import com.example.Trip.Services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping()
    public ResponseEntity<AnnouncementModel> addAnnouncement(@RequestBody AnnouncementModel announcement) {
        return ResponseEntity.ok(announcementService.addAnnouncement(announcement));
    }

    @PostMapping("/addFavorite/{userId}/{announcementId}")
    public ResponseEntity<?> addFavoriteAnnouncement(@PathVariable Integer userId, @PathVariable Integer announcementId) {
        announcementService.addFavoriteAnnouncement(userId, announcementId);
        return ResponseEntity.ok("Announcement added to favorites successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Integer id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.ok("Announcement with ID " + id + " was deleted successfully.");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Error deleting announcement: " + e.getMessage());
        }
    }

    @PostMapping("/report/{id}")
    public ResponseEntity<?> reportAnnouncement(@PathVariable Integer id) {
        announcementService.reportAnnouncement(id);
        return ResponseEntity.ok().build();
    }
}