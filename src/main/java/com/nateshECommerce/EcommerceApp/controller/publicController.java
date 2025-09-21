package com.nateshECommerce.EcommerceApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {
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
}
