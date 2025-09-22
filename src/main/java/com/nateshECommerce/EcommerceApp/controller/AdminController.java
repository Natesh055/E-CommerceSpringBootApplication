package com.nateshECommerce.EcommerceApp.controller;

import com.nateshECommerce.EcommerceApp.entity.Product;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.service.OrderService;
import com.nateshECommerce.EcommerceApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173") // React default port
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // ✅ Get all users
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if (allUsers == null || allUsers.isEmpty()) {
            log.warn("No users found in the database");
            return ResponseEntity.noContent().build();
        }
        log.info("Retrieved {} users", allUsers.size());
        return ResponseEntity.ok(allUsers);
    }

    // ✅ Get all items
    @GetMapping("/all-items")
    public ResponseEntity<List<Product>> getAllItems() {
        List<Product> allItems = orderService.getAllOrders();
        if (allItems == null || allItems.isEmpty()) {
            log.warn("No items found in the database");
            return ResponseEntity.noContent().build();
        }
        log.info("Retrieved {} items", allItems.size());
        return ResponseEntity.ok(allItems);
    }

    // ✅ Add item
    @PostMapping("/add-item")
    public ResponseEntity<Product> addItem(@RequestBody Product newProduct) {
        orderService.AddItem(newProduct); // fixed naming
        log.info("Item added: {}", newProduct.getProductName());
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // ✅ Add admin
    @PostMapping("/add-admin")
    public ResponseEntity<User> addAdmin(@RequestBody User user) {
        userService.createAdmin(user);
        log.info("Admin created with email: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // ✅ Update item
    @PutMapping("/update-item/{productName}")
    public ResponseEntity<Product> updateItem(@RequestBody Product newProduct, @PathVariable String productName) {
        boolean updated = orderService.updateItem(newProduct, productName);
        if (updated) {
            log.info("Item updated: {}", productName);
            return ResponseEntity.ok(newProduct);
        }
        log.warn("Item not found for update: {}", productName);
        return ResponseEntity.notFound().build();
    }

    // ✅ Get user by email
    @GetMapping("/email/{userMail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String userMail) {
        User user = userService.findByEmail(userMail);
        if (user == null) {
            log.warn("User not found with email: {}", userMail);
            return ResponseEntity.noContent().build();
        }
        log.info("User retrieved with email: {}", userMail);
        return ResponseEntity.ok(user);
    }

    // ✅ Delete item
    @DeleteMapping("/item/{itemName}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemName) {
        Product item = orderService.getItemByName(itemName);
        if (item == null) {
            log.warn("No item found with name: {}", itemName);
            return ResponseEntity.noContent().build();
        }
        orderService.deleteItem(item);
        log.info("Item deleted: {}", itemName);
        return ResponseEntity.ok().build();
    }

    // ✅ Delete user
    @DeleteMapping("/user/{userMail}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userMail) {
        User user = userService.findByEmail(userMail);
        if (user == null) {
            log.warn("No user found with email: {}", userMail);
            return ResponseEntity.noContent().build();
        }
        userService.deleteUser(user);
        log.info("User deleted with email: {}", userMail);
        return ResponseEntity.ok().build();
    }
}
