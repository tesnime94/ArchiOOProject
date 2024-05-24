package com.example.Trip.Services;

import com.example.Trip.Models.UserModel;
import com.example.Trip.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    public void reportUser(Integer reporterId, Integer reportedId) {
        UserModel reportedUser = userRepository.findById(reportedId).orElse(null);
        if (reportedUser != null) {
            reportedUser.getReports().add(reporterId);
            userRepository.save(reportedUser);
            if (reportedUser.getReports().size() >= 3) {
                log.info("Utilisateur avec ID: {} a été signalé plus de 3 fois", reportedId);
            }
        } else {
            log.error("Utilisateur à signaler non trouvé avec l'ID: {}", reportedId);
        }
    }
}

