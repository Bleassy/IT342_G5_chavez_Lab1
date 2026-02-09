# User Registration and Authentication System

A full-stack implementation of a user registration and authentication system built with Spring Boot and ReactJS.

## Project Structure

```
IT342_G5_<Lastname>_Lab2/
├── /backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/chavez/userauth/
│   │   │   │   ├── controller/        # REST Controllers
│   │   │   │   ├── service/           # Business Logic
│   │   │   │   ├── model/             # Entity Models
│   │   │   │   ├── repository/        # Data Access Layer
│   │   │   │   ├── dto/               # DTOs
│   │   │   │   ├── config/            # Configuration
│   │   │   │   └── UserauthApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── mvnw
├── /web
│   ├── src/
│   │   ├── components/
│   │   │   ├── Register.js
│   │   │   ├── Login.js
│   │   │   ├── Dashboard.js
│   │   │   └── ProtectedRoute.js
│   │   ├── App.js
│   │   ├── index.js
│   │   └── index.css
│   ├── public/
│   │   └── index.html
│   ├── package.json
│   └── .gitignore
├── /mobile
│   └── (Empty for now - will be implemented in Lab 3)
├── /docs
│   ├── FRS.md           # Functional Requirements Specification
│   ├── ERD.md           # Entity-Relationship Diagram
│   └── UML_DIAGRAMS.md  # UML Diagrams
├── README.md
├── TASK_CHECKLIST.md
└── pom.xml
```

## Features Implemented

### Backend (Spring Boot)
- ✅ **POST /api/auth/register** - User registration with validation
- ✅ **POST /api/auth/login** - User login with session management
- ✅ **GET /api/auth/me** - Protected endpoint to get current user
- ✅ **POST /api/auth/logout** - User logout
- ✅ **MySQL Database** - Persistent data storage
- ✅ **BCrypt Password Encryption** - Secure password storage
- ✅ **Spring Security** - Authentication and CORS configuration
- ✅ **Session Management** - Server-side session handling

### Frontend (ReactJS)
- ✅ **Register Page** - User registration form with validation
- ✅ **Login Page** - User authentication form
- ✅ **Dashboard/Profile Page** - Protected page showing user information
- ✅ **Logout Functionality** - User session termination
- ✅ **Protected Routes** - Client-side route protection
- ✅ **Responsive Design** - Mobile-friendly interface
- ✅ **Error Handling** - User-friendly error messages

## Technology Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.5.10** - Web framework
- **Spring Security** - Authentication framework
- **MySQL 8** - Database
- **Maven** - Build tool
- **Lombok** - Boilerplate reduction

### Frontend
- **React 18** - UI library
- **React Router 6** - Routing
- **Axios** - HTTP client
- **CSS3** - Styling

## Getting Started

### Prerequisites
- Java 17+
- Node.js 14+
- MySQL 8+
- Maven 3.6+
- npm or yarn

### Backend Setup

1. **Install MySQL and create database**
   ```sql
   CREATE DATABASE userauth_db;
   ```

2. **Configure database connection** in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/userauth_db
   spring.datasource.username=root
   spring.datasource.password=root
   ```

3. **Build and run**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
   Backend will run on `http://localhost:8080`

### Frontend Setup

1. **Install dependencies**
   ```bash
   cd web
   npm install
   ```

2. **Start development server**
   ```bash
   npm start
   ```
   Frontend will run on `http://localhost:3000`

## API Endpoints

### Authentication Endpoints

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890"
}

Response: 201 Created
{
  "success": true,
  "message": "User registered successfully",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-02-09T10:30:00",
    "updatedAt": "2024-02-09T10:30:00"
  }
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "SecurePass123"
}

Response: 200 OK
{
  "success": true,
  "message": "User logged in successfully",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-02-09T10:30:00",
    "updatedAt": "2024-02-09T10:30:00"
  }
}
```

#### Get Current User (Protected)
```http
GET /api/auth/me
Cookie: JSESSIONID=<session_id>

Response: 200 OK
{
  "success": true,
  "message": "User retrieved successfully",
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-02-09T10:30:00",
    "updatedAt": "2024-02-09T10:30:00"
  }
}
```

#### Logout
```http
POST /api/auth/logout
Cookie: JSESSIONID=<session_id>

Response: 200 OK
{
  "success": true,
  "message": "User logged out successfully"
}
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  phone_number VARCHAR(20),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Security Features

- **BCrypt Password Hashing** - Passwords are encrypted using BCrypt with strength 10
- **Session Management** - Server-side session storage for authentication state
- **CORS Configuration** - Controlled cross-origin requests
- **Input Validation** - All inputs are validated and sanitized
- **Protected Endpoints** - Sensitive endpoints require authentication
- **Unique Constraints** - Username and email are unique in database

## Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd web
npm test
```

## Deployment

### Backend Deployment
The Spring Boot application can be deployed as a JAR file:
```bash
mvn clean package
java -jar target/userauth-0.0.1-SNAPSHOT.jar
```

### Frontend Deployment
Build the React application for production:
```bash
npm run build
```
Deploy the contents of the `build/` directory to a static hosting service.

## Documentation

- [FRS - Functional Requirements Specification](./docs/FRS.md)
- [ERD - Entity-Relationship Diagram](./docs/ERD.md)
- [UML Diagrams](./docs/UML_DIAGRAMS.md)

## Known Limitations

- Mobile app not implemented in this phase
- No email verification implemented
- No password reset functionality
- JWT tokens not implemented (using session-based auth)
- No role-based access control (RBAC)

## Future Enhancements

- [ ] Mobile application (iOS/Android)
- [ ] JWT token-based authentication
- [ ] OAuth2 integration
- [ ] Email verification
- [ ] Password reset mechanism
- [ ] Two-factor authentication
- [ ] User profile update endpoint
- [ ] Admin dashboard

## Troubleshooting

### Backend won't start
- Ensure MySQL is running and accessible
- Verify database credentials in `application.properties`
- Check Java version is 17 or higher

### Frontend can't connect to backend
- Ensure backend is running on `http://localhost:8080`
- Check CORS configuration in backend
- Clear browser cache and cookies

### CORS errors
Most CORS issues are resolved by ensuring the backend CORS configuration includes the frontend URL.

## Contributors

- **Developer**: [Your Name]
- **Course**: IT342
- **Group**: G5
- **Submission Date**: February 9, 2024

## License

This project is for educational purposes as part of IT342 course.

## Support

For issues or questions, please refer to the documentation in the `/docs` folder or contact the development team.

---

**Note:** This is a laboratory assignment. For production use, additional security measures and comprehensive testing are recommended.
