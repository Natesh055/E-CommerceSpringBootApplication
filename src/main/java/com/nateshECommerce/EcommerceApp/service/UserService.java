package com.nateshECommerce.EcommerceApp.service;

import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
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
}
