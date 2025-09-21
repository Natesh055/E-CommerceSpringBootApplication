package com.nateshECommerce.EcommerceApp.controller;

import com.nateshECommerce.EcommerceApp.entity.Order;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.service.OrderService;
import com.nateshECommerce.EcommerceApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;


    @GetMapping("/all-users")
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

    @GetMapping("/all-items")
    public ResponseEntity<?> getAllItems()
    {
        try {
            List<Order> allItems =  orderService.getAllOrders();
            if(allItems == null || allItems.isEmpty())
            {
                log.error("Unable to find items in the database" );
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Entries found for items");
            return new ResponseEntity<>(allItems,HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to establish connection with database");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-order")
    public ResponseEntity<?> addOrder(@RequestBody Order newOrder){
        try{
            orderService.AddItem(newOrder);
            return new ResponseEntity<>(newOrder, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to create the order");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-order/{productName}")
    public ResponseEntity<?> updateOrder(@RequestBody Order newOrder,@PathVariable String productName){
        try{
            boolean orderUpdated = orderService.updateOrder(newOrder, productName);
            if(orderUpdated==true)
            {
                log.info("Order created succesfully");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            log.info("order not found with product name "+ productName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Unable to update the order of productName: "+productName);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
