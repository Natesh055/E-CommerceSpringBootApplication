package com.nateshECommerce.EcommerceApp.repository;

import com.nateshECommerce.EcommerceApp.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Product, ObjectId> {
    Product getByProductName(String productName);
}
