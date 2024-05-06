package com.example.Trip.Repository;

import com.example.Trip.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel getUserByEmail(String email);

}