# Fitness Tracker API

A Spring Boot RESTful API for managing users, workout plans, and activity logs.

---

## 📦 Features

- CRUD operations for:
  - Users
  - Workout Plans
  - Activity Logs
- Basic Authentication with Spring Security
- Role-based Access Control (ADMIN / USER)
- Bean Validation on DTOs
- Exception Handling with custom error responses
- H2 In-Memory DB for easy testing
- Swagger for API documentation

---

## 🚀 Getting Started

### Prerequisites

- JDK 21
- Spring (3.2.4)
- IDE (STS)

---

### 🛠️ Setup & Run

```bash
# Clone the repository
git clone https://github.com/hardik-boghara/fitness-tracker-service.git
cd fitness-tracker-service

# Build and run
mvn spring-boot:run
