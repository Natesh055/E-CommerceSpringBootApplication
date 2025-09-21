package com.nateshECommerce.EcommerceApp.service;

import com.nateshECommerce.EcommerceApp.entity.Order;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    public void saveOrder(Order newOrder, String userMail) {
        User user = userService.findByEmail(userMail);
        newOrder.setOrderDate(LocalDateTime.now());
        Order saved = orderRepository.save(newOrder);
        user.getOrders().add(saved);
        userService.createUser(user);
    }

    public void AddItem(Order newOrder) {
        newOrder.setOrderDate(LocalDateTime.now());
        orderRepository.save(newOrder);
    }

    public void updateOrder(Order newOrder, String productName) {
        Order existingOrder = orderRepository.getByProductName(productName);
        if (existingOrder != null) {
            existingOrder.setProductName(newOrder.getProductName()!=null && !newOrder.getProductName().equals("")?
                    newOrder.getProductName() : existingOrder.getProductName());

            existingOrder.setExistingQuantity(newOrder.getPrice()!=null && newOrder.getExistingQuantity()!=0?
                    newOrder.getExistingQuantity() : existingOrder.getExistingQuantity());

            existingOrder.setPrice(newOrder.getPrice()!=null && !newOrder.getPrice().equals("")?
                    newOrder.getPrice() : existingOrder.getPrice());
            orderRepository.save(existingOrder);
        }
    }
}
