package com.nateshECommerce.EcommerceApp.controller;

import com.nateshECommerce.EcommerceApp.entity.Order;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.service.OrderService;
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

    @Autowired
    OrderService orderService;

//    @GetMapping("email/{userMail}")
//    public ResponseEntity<?> getOrdersByEmail(@PathVariable String userMail)
//    {
//        List<Order> allOrders = userService.getAllOrders(userMail);
//    }

    @PutMapping
    public ResponseEntity<?>updateUser(@RequestBody User newUser)
    {
        String email = newUser.getEmail();
        try {
            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                existingUser.setUserName(newUser.getUserName()!=null && !newUser.getUserName().isEmpty()
                        ? newUser.getUserName() : existingUser.getUserName());
                existingUser.setPassword(newUser.getPassword()!=null && !newUser.getPassword().isEmpty()
                        ? newUser.getPassword() : existingUser.getPassword());
                existingUser.setEmail(newUser.getEmail()!=null && !newUser.getEmail().isEmpty()
                        ? newUser.getEmail() : existingUser.getEmail());
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

    @PostMapping("/email/{userMail}/{productName}")
    public ResponseEntity<?> addOrderToUser(@PathVariable String userMail, @PathVariable String productName){
        try {
            boolean isSaved = orderService.saveOrderToUser(productName, userMail);
            if (!isSaved) {
                log.warn("Unable to create order: either user not found or quantity unavailable");
                return new ResponseEntity<>("Order could not be created.", HttpStatus.BAD_REQUEST);
            }

            log.info("Order created successfully");
            return new ResponseEntity<>(productName, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Unable to create the order", e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
