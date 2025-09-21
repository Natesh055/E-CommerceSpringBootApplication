package com.nateshECommerce.EcommerceApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "userDetails")
public class User {
    @Id
    private ObjectId id;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    @Indexed(unique = true)
    private String email;
    @DBRef
    private List<Orders> orders = new ArrayList<>();
}
