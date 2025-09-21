package com.nateshECommerce.EcommerceApp.controller;

import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {

    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<?>getHealthStatus()
    {
        try {
            log.info("Health is running fine time: "+LocalDateTime.now());
            return new ResponseEntity<>("Health is running fine", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to start the application at time :" +LocalDateTime.now());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?>createUser(@RequestBody User user)
    {
        String email = user.getEmail();
        try {
            User isExistingUser = userService.findByEmail(email);
            if (isExistingUser == null) {
                //create the user
                userService.createUser(user);
                log.info("User created succesfully for user email "+ email );
                return new ResponseEntity<>("User created succesfully for email " + email, HttpStatus.OK);
            }
            log.error("Duplicate email id found for the user with email "+ email );
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("Unable to save the user in the database with email "+ email );
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
