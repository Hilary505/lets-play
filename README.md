# Let's Play - User & Product Management API

A Spring Boot REST API for managing users and products with JWT authentication, role-based authorization, and comprehensive security features.

## Requirements

- **Java 17** or higher
- **Maven 3.6+**
- **MongoDB** (Atlas or local instance)
- **Postman** or similar API testing tool

## Features

- User registration and JWT authentication
- Role-based access control (USER, ADMIN)
- Product CRUD operations with ownership validation
- Input validation and sanitization
- Global exception handling
- BCrypt password hashing
- Method-level security annotations

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/Hilary505/lets-play.git
cd lets-play
```

### 2. Configure Database
Update `src/main/resources/application.properties` with your MongoDB connection:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/letsplay
# OR for MongoDB Atlas:
# spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/database
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication (Public)
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Products
- `GET /api/products` - Get all products (public)
- `GET /api/products/{id}` - Get product by ID (public)
- `POST /api/products` - Create product (authenticated)
- `PUT /api/products/{id}` - Update product (owner/admin only)
- `DELETE /api/products/{id}` - Delete product (owner/admin only)

### Users (Admin Only)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID

## Usage Examples

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

### Create Product (with JWT token)
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"name":"Laptop","description":"Gaming laptop","price":1200.50,"stock":10}'
```

## Security Features

- **Password Hashing**: BCrypt with salt
- **JWT Authentication**: Stateless token-based auth
- **Input Validation**: Bean validation annotations
- **Input Sanitization**: MongoDB injection prevention
- **Role-based Access**: Method-level security
- **Error Handling**: Global exception handler
- **Sensitive Data Protection**: Password excluded from responses

## Testing

1. Start the application
2. Use Postman or curl to test endpoints
3. Register a user and obtain JWT token
4. Test authenticated endpoints with Bearer token
5. Verify role-based access restrictions

## Project Structure

```
src/main/java/com/letsplay/
├── config/          # Security configuration
├── controller/      # REST controllers
├── dto/            # Data transfer objects
├── exception/      # Exception handling
├── model/          # Entity models
├── repository/     # Data repositories
├── security/       # JWT and security components
├── service/        # Business logic
├── util/           # Utility classes
└── LetsPlayApplication.java
```