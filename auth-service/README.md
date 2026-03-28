# 🔐 Auth Service (YouTube Clone)

The **Auth Service** is a core microservice responsible for user authentication, authorization, and session management. It handles secure registration, login, and token issuance while integrating with Redis for session persistence and Kafka for cross-service communication.

---

## 🛠 Tech Stack

* **Java 21** & **Spring Boot 3.4+**
* **Spring Security 6** (Stateless JWT Strategy)
* **PostgreSQL** (User credentials storage)
* **Redis** (Token whitelist/blacklist & Session management)
* **Apache Kafka** (Events: `UserRegistered`, `UserLoggedIn`)
* **SpringDoc OpenAPI** (Swagger UI for API testing)

---

## 🚀 Getting Started

### Prerequisites
Ensure you have the following services running (Docker recommended):
* **PostgreSQL** (Port: `5432`)
* **Redis** (Port: `6379`)
* **Kafka** (Port: `9092`)

### Installation & Run
1.  Navigate to the service directory:
    ```bash
    cd auth-service
    ```
2.  Build and run the application:
    ```bash
    ./gradlew bootRun
    ```
The service will start on `http://localhost:8081`.

---

## 📖 API Documentation

The interactive API documentation is automatically generated and can be accessed at:
👉 **[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)**



---

## 🏗 Key Features

### 1. Dual-Token Authentication
Implements a secure **Access/Refresh Token** pattern:
* **Access Token**: Short-lived (e.g., 60m), passed in the `Authorization` header.
* **Refresh Token**: Long-lived, stored in a **HttpOnly Secure Cookie** to prevent XSS attacks.

### 2. Event-Driven Integration
Upon successful registration or login, events are published to Kafka:
* `user-registration-topic`: Notifies the **User Profile Service** to initialize user data.
* `user-login-topic`: Used for analytics or security auditing.

### 3. Redis Session Management
Stores active refresh tokens in Redis to allow **Global Logout** and token revocation (Blacklisting).

---

## ⚙️ Configuration (`application.properties`)

| Property | Description | Default Value |
| :--- | :--- | :--- |
| `server.port` | Service port | `8081` |
| `jwt.access.secret` | HMAC secret for Access Tokens | `[RESTRICTED]` |
| `spring.datasource.url` | Database connection string | `jdbc:postgresql://localhost:5432/auth-service` |

---

## 🔒 Security
* **Passwords**: Hashed using **BCrypt** with a strength of 12.
* **CORS**: Pre-configured to allow requests from local frontend dev servers (`localhost:3000`, `5173`).
* **CSRF**: Disabled as the service uses stateless JWT tokens.

---

## 📂 Project Structure
This service is part of the YouTube Clone project.
```text
.
├── auth-service/       <-- You are here
├── gateway/            (Next step)
├── video-service/      (Upcoming)
└── README.md           (Root Project Overview)

```
---
