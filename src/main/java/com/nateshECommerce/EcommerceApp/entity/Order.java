package com.nateshECommerce.EcommerceApp.entity;


import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
@Component
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    private ObjectId orderId;
    @Nonnull
    private String productName;
    private LocalDateTime orderDate;
    private Double price;
    @Nonnull
    private Double existingQuantity;
}
