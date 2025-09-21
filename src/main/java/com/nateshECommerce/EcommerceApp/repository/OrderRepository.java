package com.nateshECommerce.EcommerceApp.repository;

import com.nateshECommerce.EcommerceApp.entity.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
}
