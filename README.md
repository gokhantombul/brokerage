# 📈 Brokerage Firm Backend API - Java Spring Boot

This project is a backend application developed using Spring Boot to manage stock orders for a brokerage firm.

## 🚀 Features

- Create stock orders on behalf of a customer
- List orders filtered by customer and date range
- Cancel pending orders
- List all assets of a customer
- Admin endpoint for matching orders (Bonus)
- Customer login system (Bonus)
- H2 in-memory database
- Extensive unit test coverage using JUnit and Mockito

## 📁 API Endpoints

### 🔐 Authentication
```
POST /auth/login
Body: { "username": "user1", "password": "pass1" }
```

### 📝 Orders
```
POST /orders
Body: {
  "customerId": 2,
  "assetName": "AAPL",
  "side": "BUY",
  "size": 10,
  "price": 100
}

DELETE /orders/{id}?customerId=2

GET /orders?customerId=2&start=2024-01-01T00:00:00&end=2025-01-01T00:00:00
```

### 💼 Assets
```
GET /assets?customerId=2
```

### 🛠️ Admin Operations
```
POST /admin/match/{orderId}
```

## 🛠️ Setup

### Prerequisites
- Java 17+
- Maven

### Run the Project
```
git clone https://github.com/your-username/brokerage-api.git
cd brokerage-api
mvn spring-boot:run
```

### H2 Console
- `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

## 🧪 Running Tests
```
mvn test
```

## 👤 Default Users
| Username | Password | Role    |
|----------|----------|---------|
| gokhantombul@hotmail.com    | 1234     | Admin   |

## 📄 License
MIT

## ✍️ Author
Gokhan TOMBUL - Java Backend Developer Challenge Project for Ing Hubs Turkiye
