# 📈 Brokerage Firm Backend API

A production-ready Spring Boot backend for a fictional brokerage firm to manage customer stock orders and asset tracking with role-based access.

## 🚀 Features
- ✅ Create, cancel, and match stock orders (BUY/SELL)
- 📊 Track customer assets including TRY
- 🔐 Admin/customer authorization with role control
- 🧪 Includes unit and integration tests
- 🛡️ Auth secured endpoints (Admin & Customer login)
- 💾 In-memory H2 database for development

## 🛠️ Tech Stack
- Java 21
- Spring Boot 3.5.0
- Spring Security
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5 + Mockito

## 🧑‍💻 How to Run

### 🔧 Prerequisites
- Java 17+
- Maven 3.8+

### 💻 Running with Maven
```bash
mvn clean install
mvn spring-boot:run
```

## 🔌 API Endpoints
| Method | Endpoint                   | Description                            |
|--------|----------------------------|----------------------------------------|
| POST   | `/api/orders`             | Create new stock order                 |
| GET    | `/api/orders`             | List customer orders by date range     |
| DELETE | `/api/orders/{id}`        | Cancel a pending order                 |
| GET    | `/api/assets`             | List all customer assets               |
| POST   | `/api/orders/{id}/match`  | Match and finalize a pending order     |
| POST   | `/api/v1/auth/signin`    | Customer login                   |

## 🧪 Tests
- Unit tests for service layer
- Integration tests for API endpoints
- Run with:
```bash
mvn test
```

## 🧾 H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC: `jdbc:h2:mem:brokeragedb`
- User: `sa`
- Password: `password`

## 📦 Deployment
Package the app into a jar:
```bash
mvn clean package
java -jar target/brokerage-api.jar
```
