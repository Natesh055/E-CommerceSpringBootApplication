package com.nateshECommerce.EcommerceApp.repository;


import com.nateshECommerce.EcommerceApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByEmail(String email);
}
