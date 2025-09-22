package com.nateshECommerce.EcommerceApp.controller;

import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.service.OrderService;
import com.nateshECommerce.EcommerceApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/get-details")
    public ResponseEntity<?> getUserDetailsWhenLoggedIn() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.error("Authentication failed or user is not authenticated.");
                throw new SecurityException("User is not authenticated.");
            }
            String email = authentication.getName();
            log.info("Attempting to retrieve user with email: {}", email);
            User user = userService.findByEmail(email);

            if (user == null) {
                log.error("User not found for email: {}", email);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            log.info("Successfully retrieved user with email: {}", email);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (SecurityException se) {
            log.error("Security error: {}", se.getMessage());
            throw se; // Rethrow or return a custom error response
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred.", e); // Or a custom error response
        }
    }


    @GetMapping("email/get-orders")
    public ResponseEntity<?> getOrdersByEmail() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.error("Authentication failed or user is not authenticated.");
                throw new SecurityException("User is not authenticated.");
            }
            String email = authentication.getName();
            log.info("Attempting to retrieve user with email: {}", email);
            User user = userService.findByEmail(email);

            log.info("Successfully retrieved user with email: {}", email);
            return new ResponseEntity<>(user.getProducts(), HttpStatus.OK);
        } catch (SecurityException se) {
            log.error("Security error: {}", se.getMessage());
            throw se; // Rethrow or return a custom error response
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred.", e); // Or a custom error response
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Check if user is authenticated
            if (authentication == null || !authentication.isAuthenticated()) {
                log.error("Authentication failed or user is not authenticated.");
                throw new SecurityException("User is not authenticated.");
            }

            String email = authentication.getName();
            log.info("Attempting to retrieve user with email: {}", email);

            // Retrieve the existing user
            User existingUser = userService.findByEmail(email);

            // Check if user exists
            if (existingUser != null) {
                // Update user fields only if new values are provided
                existingUser.setUserName(newUser.getUserName() != null && !newUser.getUserName().isEmpty() ? newUser.getUserName() : existingUser.getUserName());
                existingUser.setPassword(newUser.getPassword() != null && !newUser.getPassword().isEmpty() ? newUser.getPassword() : existingUser.getPassword());

                // Save the updated user
                userService.createUser(existingUser);
                log.info("User updated successfully with emailId: {}", email);
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            }

            // If user not found, log and return a NOT_FOUND response
            log.error("No user found with emailId: {}", email);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (SecurityException se) {
            log.error("Security exception: {}", se.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Unable to update user in the database. Error: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add-order/{productName}")
    public ResponseEntity<?> addOrderToUser(@PathVariable String productName) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                log.error("Authentication failed or user is not authenticated.");
                throw new SecurityException("User is not authenticated.");
            }

            String userEmail = authentication.getName();
            log.info("Attempting to retrieve user with email: {}", userEmail);

            User user = userService.findByEmail(userEmail);
            if (user == null) {
                log.warn("User not found with email: {}", userEmail);
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }

            log.info("Successfully retrieved user with email: {}", userEmail);

            boolean isSaved = orderService.saveOrderToUser(productName, userEmail);

            if (!isSaved) {
                log.warn("Unable to create order for product '{}' and user '{}': either user not found or quantity unavailable", productName, userEmail);
                return new ResponseEntity<>("Order could not be created.", HttpStatus.BAD_REQUEST);
            }

            log.info("Order for product '{}' created successfully for user '{}'", productName, userEmail);
            return new ResponseEntity<>(productName, HttpStatus.OK);

        } catch (SecurityException se) {
            log.error("Security exception: {}", se.getMessage());
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Unable to create the order due to an unexpected error", e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
