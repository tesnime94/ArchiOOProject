package com.example.Trip.Controleur;

import com.example.Trip.Models.UserModel;
import com.example.Trip.Services.ReportService;
import com.example.Trip.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControler {

    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;

    @GetMapping()
    public List<UserModel> getUser() {
        return userService.getUser();
    }

    @GetMapping("/login")
    public Integer login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteAccountById(id);
    }

    @PostMapping("/update/{id}")
    public UserModel updateUser(@PathVariable Integer id, @RequestBody UserModel updatedUser) {
        return userService.updateAccount(id, updatedUser.getName(), updatedUser.getSurname(),updatedUser.getBirth(),updatedUser.getPassword(), updatedUser.getAddress(),updatedUser.getPhoneNumber(), updatedUser.getEmail());
    }

    @PostMapping()
    public UserModel saveUser(@RequestBody UserModel userPo) {
        return userService.createAccount(userPo);
    }

    @PostMapping("/report")
    public void reportUser(@RequestParam Integer reporterId, @RequestParam Integer reportedId) {
        reportService.reportUser(reporterId, reportedId);
    }
}


