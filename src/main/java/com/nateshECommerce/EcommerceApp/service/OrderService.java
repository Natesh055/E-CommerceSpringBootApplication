package com.nateshECommerce.EcommerceApp.service;

import com.nateshECommerce.EcommerceApp.entity.Order;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

//    @Transactional
    public boolean saveOrderToUser(String productName, String userMail) {
        User user = userService.findByEmail(userMail);
        if(user == null) {
            return false; // user not found
        }
        Order newOrder = orderRepository.getByProductName(productName);
        newOrder.setOrderDate(LocalDateTime.now());
        Order saved = orderRepository.save(newOrder);
        if (saved.getExistingQuantity() > 0) {
            saved.setExistingQuantity(saved.getExistingQuantity() - 1);
            orderRepository.save(saved);

            user.getOrders().add(saved);
            userService.createUser(user); // make sure this updates existing user
            return true;
        }
        return false; // no quantity available
    }

    public List<Order> getAllOrders()
    {
        return orderRepository.findAll();
    }

    public void AddItem(Order newOrder) {
        newOrder.setOrderDate(LocalDateTime.now());
        orderRepository.save(newOrder);
    }

    public boolean updateOrder(Order newOrder, String productName) {
        Order existingOrder = orderRepository.getByProductName(productName);
        if (existingOrder != null) {
            existingOrder.setProductName(newOrder.getProductName()!=null && !newOrder.getProductName().equals("")?
                    newOrder.getProductName() : existingOrder.getProductName());

            existingOrder.setExistingQuantity(newOrder.getExistingQuantity()!=null && newOrder.getExistingQuantity()!=0?
                    newOrder.getExistingQuantity() : existingOrder.getExistingQuantity());

            existingOrder.setPrice(newOrder.getPrice()!=null && !newOrder.getPrice().equals("")?
                    newOrder.getPrice() : existingOrder.getPrice());
            orderRepository.save(existingOrder);
            return true;
        }
        return false;
    }
}
