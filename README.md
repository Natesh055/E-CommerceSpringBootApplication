# E-commerce Backend System

**Technologies:** Spring Boot, MongoDB, Spring Security, SLF4J

A backend system for an e-commerce application that manages users, products, and orders with secure authentication and role-based access control. Designed with clean architecture, service-controller separation, and proper logging.

---

## Features

* **User Management**

  * User registration with unique email validation
  * Role-based authentication (USER / ADMIN)
  * Passwords securely stored using BCrypt

* **Product Management**

  * CRUD operations on products
  * Inventory tracking with automatic quantity updates on orders

* **Order Management**

  * Users can place orders for products
  * Orders linked to users with MongoDB `@DBRef` relationships

* **Security**

  * Role-based access control using Spring Security
  * Public, user, and admin endpoints
  * Basic authentication implemented, ready for JWT enhancement

* **Logging & Monitoring**

  * SLF4J used for info, warning, and error logs
  * Tracks API requests, errors, and important events

---

## API Endpoints

* **Public** (`/public`)

  * `POST /create-user` → Create a new user
  * `GET /` → Health check

* **User** (`/user`)

  * `GET /get-details` → Fetch logged-in user details
  * `GET /all-items` → Get all products
  * `POST /add-order/{productName}` → Place order

* **Admin** (`/admin`)

  * `GET /all-users` → Get all users
  * `GET /all-items` → Get all products
  * `POST /add-item` → Add a new product
  * `PUT /update-item/{productName}` → Update a product
  * `DELETE /item/{itemName}` → Delete a product
  * `POST /add-admin` → Create a new admin

---

## Setup & Running

1. **Clone the repository:**

```bash
git clone https://github.com/Natesh055/E-CommerceSpringBootApplication.git
cd EcommerceApp
```

2. **Configure MongoDB:**

* Ensure MongoDB is running locally or update `application.properties` with your connection string.

3. **Build and run the application:**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Access APIs:**

* Default Base URL: `http://localhost:8080/`

---

## Future Enhancements

* JWT-based authentication for stateless security
* Global exception handling with `@ControllerAdvice`
* DTOs for API responses to separate entity and response structure
* API documentation using Swagger

