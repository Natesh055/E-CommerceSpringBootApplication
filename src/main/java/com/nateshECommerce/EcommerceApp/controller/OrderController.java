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

@Component
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    @PostMapping("/email/{userMail}")
    public ResponseEntity<?> createOrder(@RequestBody Order newOrder, @PathVariable String userMail){
        try{
            orderService.saveOrder(newOrder,userMail);
            log.info("Order create succesfully");
            return new ResponseEntity<>(newOrder, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Unable to create the order");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
