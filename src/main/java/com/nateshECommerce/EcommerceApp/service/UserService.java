package com.nateshECommerce.EcommerceApp.service;

import com.nateshECommerce.EcommerceApp.entity.Order;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.repository.OrderRepository;
import com.nateshECommerce.EcommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public void createUser(User user)
    {
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
}
