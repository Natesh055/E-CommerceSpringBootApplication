package com.nateshECommerce.EcommerceApp.controller;

import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers()
    {
        try {
            List<User> allUsers =  userService.getAllUsers();
            if(allUsers == null || allUsers.isEmpty())
            {
                log.error("Unable to find users in the database" );
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Entries found for users");
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to establish connection with database");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
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

    @PutMapping
    public ResponseEntity<?>updateUser(@RequestBody User newUser)
    {
        String email = newUser.getEmail();
        try {
            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                existingUser.setUserName(newUser.getUserName()!=null && !newUser.getEmail().equals("")
                ?newUser.getUserName(): existingUser.getUserName());
                existingUser.setPassword(newUser.getPassword()!=null && !newUser.getPassword().equals("")
                        ?newUser.getPassword(): existingUser.getPassword());
                existingUser.setEmail(newUser.getEmail()!=null && !newUser.getEmail().equals("")
                        ?newUser.getEmail(): existingUser.getEmail());
                userService.createUser(existingUser);
                log.error("user updated succesfully with emailId: "+ email );
                return new ResponseEntity<>(existingUser,HttpStatus.OK);
            }
            log.error("No user with email id found for email "+ email );
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unable to save the user in the database with email "+ email );
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{userMail}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String userMail)
    {
        try {
            User user = userService.findByEmail(userMail);
            if(user == null)
            {
                log.error("Unable to find user in the database");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Entries found for the user with email: "+userMail);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to establish connection with database");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
