package com.nateshECommerce.EcommerceApp.service;

import com.nateshECommerce.EcommerceApp.entity.Product;
import com.nateshECommerce.EcommerceApp.entity.User;
import com.nateshECommerce.EcommerceApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

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
    Product newProduct = orderRepository.getByProductName(productName);
    newProduct.setOrderDate(LocalDateTime.now());
    Product saved = orderRepository.save(newProduct);
    if (saved.getExistingQuantity() > 0) {
        saved.setExistingQuantity(saved.getExistingQuantity() - 1);
        orderRepository.save(saved);

        user.getProducts().add(saved);
        userService.updateUser(user);
        return true;
    }
    return false; // no quantity available
}


    public List<Product> getAllOrders()
    {
        return orderRepository.findAll();
    }
    public Product getItemByName(String itemName)
    {
        return orderRepository.getByProductName(itemName);
    }

    public void AddItem(Product newProduct) {
        newProduct.setOrderDate(LocalDateTime.now());
        orderRepository.save(newProduct);
    }

    public boolean updateItem(Product newProduct, String productName) {
        Product existingProduct = orderRepository.getByProductName(productName);
        if (existingProduct != null) {
            existingProduct.setProductName(newProduct.getProductName()!=null && !newProduct.getProductName().equals("")?
                    newProduct.getProductName() : existingProduct.getProductName());

            existingProduct.setExistingQuantity(newProduct.getExistingQuantity()!=null && newProduct.getExistingQuantity()!=0?
                    newProduct.getExistingQuantity() : existingProduct.getExistingQuantity());

            existingProduct.setPrice(newProduct.getPrice()!=null && !newProduct.getPrice().equals("")?
                    newProduct.getPrice() : existingProduct.getPrice());
            orderRepository.save(existingProduct);
            return true;
        }
        return false;
    }

    public void deleteItem(Product product)
    {
        orderRepository.delete(product);
    }
}
