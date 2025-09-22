package com.nateshECommerce.EcommerceApp.service;

import com.nateshECommerce.EcommerceApp.entity.Order;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.repository.OrderRepository;
import com.nateshECommerce.EcommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public void createUser(User user)
    {
        user.setRoles(Arrays.asList("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void updateUser(User user)
    {
        userRepository.save(user);
    }
    public void createAdmin(User user)
    {
        user.setRoles(Arrays.asList("ADMIN","USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
    public void saveOrder(Order newOrder, String userMail) {
        User user = userRepository.findByEmail(userMail);
        newOrder.setOrderDate(LocalDateTime.now());
        Order saved = orderRepository.save(newOrder);
        user.getOrders().add(saved);
        userRepository.save(user);
    }
//    public List<Order> getAllOrdersByUser(User user)
//    {
//        return orderRepository.
//    }
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }
}
