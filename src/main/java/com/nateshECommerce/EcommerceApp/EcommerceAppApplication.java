package com.nateshECommerce.EcommerceApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EcommerceAppApplication {

	public static void main(String[] args) {
//        Dotenv dotenv = Dotenv.load();
//        System.setProperty("spring.data.mongodb.uri", dotenv.get("MONGODB_URI"));
//        System.setProperty("spring.data.mongodb.database", dotenv.get("MONGO_DATABASE"));
		SpringApplication.run(EcommerceAppApplication.class, args);
	}

}
