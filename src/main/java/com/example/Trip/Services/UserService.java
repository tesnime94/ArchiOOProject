package com.example.Trip.Services;

import com.example.Trip.Models.UserModel;
import com.example.Trip.Models.VoyageModel;
import com.example.Trip.Repository.UserRepository;
import com.example.Trip.Repository.VoyageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public List<UserModel> getUser() {
        return userRepository.findAll();
    }

    public void deleteAccountById(Integer id) {
        UserModel userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete != null) {
            userRepository.delete(userToDelete);
        } else {
            log.error("Utilisateur non trouvé avec l'ID: {}", id);
        }
    }

    public UserModel updateAccount(Integer id, String name, String surname, String birth, String password, String address, Integer phoneNumber, String email) {
        UserModel existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            if (surname != null) {
                existingUser.setSurname(surname);
            }
            if (birth != null) {
                existingUser.setBirth(birth);
            }
            if (name != null) {
                existingUser.setName(name);
            }
            if (email != null) {
                existingUser.setEmail(email);
            }
            if (address != null) {
                existingUser.setAddress(address);
            }
            if (password != null) {
                existingUser.setPassword(password);
            }
            if (phoneNumber != null) {
                existingUser.setPhoneNumber(phoneNumber);
            }

            return userRepository.save(existingUser);
        } else {
            log.error("Utilisateur non trouvé avec l'ID: {}", id);
            return null;
        }
    }

    public UserModel createAccount(UserModel userPo) {
        return userRepository.save(userPo);
    }

    public Integer login(String email, String password) {
        var user = userRepository.getUserByEmail(email);
        if (user == null) return 0;
        return user.getPassword().equals(password) ? user.getId() : 0;
    }

    public void assignVoyageToUser(Integer userId, Integer voyageId) {
        UserModel user = userRepository.findById(userId).orElse(null);
        VoyageModel voyage = voyageRepository.findById(voyageId).orElse(null);

        if (user != null && voyage != null) {
            log.info("Utilisateur trouvé: {}", user.getEmail());
            log.info("Voyage trouvé: {}", voyage.getName());
            if (!user.getVoyages().contains(voyage)) {
                user.getVoyages().add(voyage);
                userRepository.save(user);
                log.info("Voyage assigné à l'utilisateur.");
            }
            if (!voyage.getUsers().contains(user)) {
                voyage.getUsers().add(user);
                voyageRepository.save(voyage);
                log.info("Utilisateur assigné au voyage.");
            }
        } else {
            if (user == null) {
                log.error("Utilisateur non trouvé avec l'ID: {}", userId);
            }
            if (voyage == null) {
                log.error("Voyage non trouvé avec l'ID: {}", voyageId);
            }
        }
    }
}