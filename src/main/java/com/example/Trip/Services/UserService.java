package com.example.Trip.Services;

import com.example.Trip.Models.UserModel;
import com.example.Trip.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    public List<UserModel> getUser() {

        return userRepository.findAll();
    }

    public void deleteAccountById(Integer id){
        UserModel userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete != null) {
            userRepository.delete(userToDelete);
        } else {
            log.error("Utilisateur non trouvé avec l'ID: {}", id);
        }
    }

    public UserModel updateAccount(Integer id, String name, String surname, String birth, String password, String address, Integer phoneNumber, String email){
        UserModel existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            if (surname != null) {
                existingUser.setSurname(surname);
            }
            if(birth !=null){
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

    public Integer login (String email, String password) {
        var user = userRepository.getUserByEmail(email);
        if (user==null) return 0;
        return user.getPassword().equals(password)? user.getId():0 ;
    }

}
