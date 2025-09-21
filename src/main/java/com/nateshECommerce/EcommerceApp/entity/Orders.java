package com.nateshECommerce.EcommerceApp.entity;


import jakarta.annotation.Nonnull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Orders {
    @Id
    private ObjectId orderId;
    @Nonnull
    private String productName;
    private LocalDateTime orderDate;
    private Double price;
}
