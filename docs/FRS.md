# Functional Requirements Specification (FRS)
## User Registration and Authentication System

**Document Version**: 1.0  
**Date**: February 9, 2024  
**Course**: IT342  
**Group**: G5  

---

## 1. Executive Summary

This document provides a comprehensive specification of the User Registration and Authentication System. The system enables users to register new accounts, authenticate with credentials, and access protected resources through a web-based interface backed by a Spring Boot REST API.

---

## 2. Functional Requirements

### 2.1 User Registration (FR-001)

**Description**: System shall allow new users to create an account.

**Acceptance Criteria**:
- User can enter username, email, password, and optional personal information
- System validates input data (username and email uniqueness)
- Password is encrypted using BCrypt before storage
- Confirmation message is displayed upon successful registration
- User is redirected to login page after successful registration
- Error messages are displayed for validation failures

**API Endpoint**: `POST /api/auth/register`

**Input**:
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "phoneNumber": "string"
}
```

**Output**:
```json
{
  "success": true/false,
  "message": "string",
  "user": {
    "id": "number",
    "username": "string",
    "email": "string",
    "firstName": "string",
    "lastName": "string",
    "phoneNumber": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

### 2.2 User Login (FR-002)

**Description**: System shall authenticate users based on username and password.

**Acceptance Criteria**:
- User can enter username and password
- System verifies credentials against stored encrypted passwords
- Session is created upon successful authentication
- User information is returned
- Error message is displayed for invalid credentials
- User is redirected to dashboard upon successful login

**API Endpoint**: `POST /api/auth/login`

**Input**:
```json
{
  "username": "string",
  "password": "string"
}
```

**Output**:
```json
{
  "success": true/false,
  "message": "string",
  "user": {
    "id": "number",
    "username": "string",
    "email": "string",
    "firstName": "string",
    "lastName": "string",
    "phoneNumber": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

### 2.3 Get Current User (FR-003)

**Description**: System shall retrieve authenticated user information.

**Acceptance Criteria**:
- Endpoint requires valid session
- User data is returned for authenticated users
- Unauthorized error is returned for unauthenticated requests
- Protected from unauthorized access

**API Endpoint**: `GET /api/auth/me`

**Requirements**: Valid session cookie

**Output**:
```json
{
  "success": true/false,
  "message": "string",
  "user": {
    "id": "number",
    "username": "string",
    "email": "string",
    "firstName": "string",
    "lastName": "string",
    "phoneNumber": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp"
  }
}
```

### 2.4 User Logout (FR-004)

**Description**: System shall terminate user session.

**Acceptance Criteria**:
- Session is invalidated
- User is navigated to login page
- User can no longer access protected resources
- Confirmation message is displayed

**API Endpoint**: `POST /api/auth/logout`

**Output**:
```json
{
  "success": true,
  "message": "User logged out successfully"
}
```

### 2.5 Dashboard/Profile Page (FR-005)

**Description**: System shall display user information on protected dashboard.

**Acceptance Criteria**:
- Page is only accessible to authenticated users
- User profile information is displayed
- Page includes logout button
- Unauthenticated users are redirected to login
- Page shows registration timestamp and last update time

**Content**:
- User ID
- Username
- Email
- First Name & Last Name
- Phone Number
- Registration Date & Time
- Last Updated Date & Time

---

## 3. Non-Functional Requirements

### 3.1 Security (NFR-001)
- All passwords must be encrypted using BCrypt (strength 10)
- Passwords must never be stored in plain text
- Session-based authentication with HttpSession
- CORS restrictions enforced
- Input validation and sanitization

### 3.2 Performance (NFR-002)
- API responses must complete within 2 seconds
- Database queries must be optimized
- Connection pooling implemented

### 3.3 Scalability (NFR-003)
- System designed to handle 1000+ concurrent users
- Database designed for horizontal scaling
- Stateless API endpoints (except session storage)

### 3.4 Usability (NFR-004)
- User interface is intuitive and responsive
- Error messages are clear and actionable
- Mobile-friendly design
- Support for common browsers

### 3.5 Reliability (NFR-005)
- 99% uptime target
- Automatic database connection retry
- Error logging and monitoring

---

## 4. Entity-Relationship Diagram (ERD)

### Database Schema

```
+------------------+
|      USERS       |
+------------------+
| PK id            |
| username (UQ)    |
| email (UQ)       |
| password         |
| first_name       |
| last_name        |
| phone_number     |
| created_at       |
| updated_at       |
+------------------+
```

### Field Descriptions

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PK, AUTO_INCREMENT | Primary key, auto-generated |
| username | VARCHAR(255) | NOT NULL, UNIQUE | User's login username |
| email | VARCHAR(255) | NOT NULL, UNIQUE | User's email address |
| password | VARCHAR(255) | NOT NULL | Encrypted password (BCrypt) |
| first_name | VARCHAR(255) | NULLABLE | User's first name |
| last_name | VARCHAR(255) | NULLABLE | User's last name |
| phone_number | VARCHAR(20) | NULLABLE | User's contact number |
| created_at | TIMESTAMP | NOT NULL | Account creation timestamp |
| updated_at | TIMESTAMP | NOT NULL | Last update timestamp |

---

## 5. UML Diagrams

### 5.1 Use Case Diagram

```
┌─────────────────┐
│     SYSTEM      │
├─────────────────┤
│ User Auth App   │
└─────────────────┘
        │
    ┌───┴───┐
    │       │
    ▼       ▼
┌────────┐ ┌──────────┐
│ User   │ │ Unauth   │
├────────┤ │ User     │
│ - ID   │ └──────────┘
│ - name │
└────────┘
    ▲
    │
    │ uses
    │
    ├─────────────────────┐
    │                     │
    ▼                     ▼
(register)          (login)
    │                     │
    └─────────────────────┘
            │
            ▼
      (view dashboard)
            │
            ▼
      (logout)
```

### 5.2 Sequence Diagram - Registration Flow

```
User        Frontend       Backend        Database
 │              │             │              │
 │ Register     │             │              │
 ├─────────────>│             │              │
 │              │ POST request│              │
 │              ├────────────>│              │
 │              │             │ Validate    │
 │              │             │ Input       │
 │              │             │ Check unique│
 │              │             ├────────────>│
 │              │             │ Query      │
 │              │             │<────────────┤
 │              │             │             │
 │              │             │ Hash PWD   │
 │              │             │ Save User  │
 │              │             ├────────────>│
 │              │             │ Insert     │
 │              │             │<────────────┤
 │              │ 201 Created │             │
 │              │<────────────┤             │
 │ Success      │             │             │
 │<─────────────┤             │             │
 │ Redirect     │             │             │
 │ to Login     │             │             │
```

### 5.3 Sequence Diagram - Login Flow

```
User        Frontend       Backend        Database
 │              │             │              │
 │ Login        │             │              │
 ├─────────────>│             │              │
 │              │ POST request│              │
 │              ├────────────>│              │
 │              │             │ Find User  │
 │              │             ├────────────>│
 │              │             │ Query      │
 │              │             │<────────────┤
 │              │             │             │
 │              │             │ Verify PWD │
 │              │             │ Create     │
 │              │             │ Session    │
 │              │ 200 OK      │             │
 │              │<────────────┤             │
 │ Success      │             │             │
 │<─────────────┤             │             │
 │ Redirect     │             │             │
 │ to Dashboard │             │             │
```

### 5.4 Sequence Diagram - Protected Access

```
User        Frontend       Backend        Session
 │              │             │              │
 │ View Profile│             │              │
 ├─────────────>│             │              │
 │              │ GET /me     │              │
 │              │ + Session   │              │
 │              ├────────────>│              │
 │              │             │ Validate   │
 │              │             │ Session    │
 │              │             ├────────────>│
 │              │             │ Check      │
 │              │             │<────────────┤
 │              │             │             │
 │              │             │ Fetch User │
 │              │ 200 OK      │ Data       │
 │              │<────────────┤             │
 │ Show Profile │             │             │
 │<─────────────┤             │             │
```

### 5.5 Class Diagram

```
┌─────────────────────────┐
│       User Entity       │
├─────────────────────────┤
│ - id: Long              │
│ - username: String      │
│ - email: String         │
│ - password: String      │
│ - firstName: String     │
│ - lastName: String      │
│ - phoneNumber: String   │
│ - createdAt: DateTime   │
│ - updatedAt: DateTime   │
├─────────────────────────┤
│ + User()                │
│ + getId(): Long         │
│ + setId(Long)           │
│ + getUsername(): String │
│ + setUsername(String)   │
└─────────────────────────┘
         ▲
         │
         │ implements
         │
┌─────────────────────────┐
│   UserRepository        │
├─────────────────────────┤
│ + findByUsername()      │
│ + findByEmail()         │
│ + existsByUsername()    │
│ + existsByEmail()       │
│ + save()                │
│ + findById()            │
└─────────────────────────┘


┌────────────────────────────┐
│    UserService             │
├────────────────────────────┤
│ - userRepository           │
│ - passwordEncoder          │
├────────────────────────────┤
│ + registerUser()           │
│ + authenticateUser()       │
│ + getUserById()            │
│ + getUserByUsername()      │
│ - mapToUserResponse()      │
└────────────────────────────┘


┌────────────────────────────┐
│    AuthController          │
├────────────────────────────┤
│ - userService              │
├────────────────────────────┤
│ + register()               │
│ + login()                  │
│ + getCurrentUser()         │
│ + logout()                 │
└────────────────────────────┘
```

---

## 6. User Interface Specifications

### 6.1 Register Page
- Form fields: Username, Email, Password, First Name, Last Name, Phone
- Required fields: Username, Email, Password
- Validation feedback in real-time
- Submit button disabled while loading
- Success/Error alert messages
- Link to login page

### 6.2 Login Page
- Form fields: Username, Password
- Required fields: Username, Password
- "Remember Me" functionality (optional)
- Submit button disabled while loading
- Error message on invalid credentials
- Link to registration page

### 6.3 Dashboard Page
- Header with navigation (user greeting, dashboard link, logout)
- User profile information section
- Profile data displayed in read-only format
- Logout button in button group
- Success indicator message
- Profile image placeholder (future enhancement)

---

## 7. Technical Architecture

### 7.1 Backend Architecture

```
┌──────────────────────┐
│ Request Handler      │
│ (AuthController)     │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Business Logic       │
│ (UserService)        │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Data Access          │
│ (UserRepository)     │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Database             │
│ (MySQL)              │
└──────────────────────┘
```

### 7.2 Frontend Architecture

```
┌──────────────────────┐
│ App.js (Router)      │
└──────────┬───────────┘
           │
    ┌──────┴──────┬────────┬───────────┐
    │             │        │           │
    ▼             ▼        ▼           ▼
┌─────────┐ ┌────────┐ ┌────────┐ ┌────────┐
│Register │ │ Login  │ │Dashboard│Protected│
└─────────┘ └────────┘ └────────┘ │Route   │
                                   └────────┘
```

---

## 8. Data Flow Diagrams

### 8.1 Registration Data Flow

```
User Input
    │
    ▼
Register Component
    │
    ├─ Validate Input
    │
    ▼
POST /api/auth/register
    │
    ▼
AuthController.register()
    │
    ├─ Check Username Unique
    ├─ Check Email Unique
    ├─ Hash Password (BCrypt)
    │
    ▼
UserService.registerUser()
    │
    ▼
UserRepository.save()
    │
    ▼
MySQL Database
    │
    ▼
Response to Frontend
    │
    ▼
User Redirected to Login
```

### 8.2 Login Data Flow

```
User Input (Username, Password)
    │
    ▼
Login Component
    │
    ├─ Validate Input
    │
    ▼
POST /api/auth/login
    │
    ▼
AuthController.login()
    │
    ├─ Find User by Username
    ├─ Verify Password (BCrypt)
    ├─ Create Session
    │
    ▼
UserService.authenticateUser()
    │
    ▼
UserRepository.findByUsername()
    │
    ▼
MySQL Database
    │
    ▼
Response + Session Cookie
    │
    ▼
Store in Browser + Redirect
    │
    ▼
Dashboard Page
```

---

## 9. Constraints and Assumptions

### 9.1 Constraints
- Password minimum 8 characters (recommended)
- Username must be 3-50 characters
- Email must follow valid format
- Maximum request timeout: 30 seconds
- Database connection pool size: 10

### 9.2 Assumptions
- Users have valid email addresses
- One user per email address
- One user per username
- Single login session per user (latest login replaces previous)
- Browser supports cookies for session storage

---

## 10. Testing Requirements

### 10.1 Unit Testing
- User entity validation
- Password encryption/verification
- Input validation logic
- Response mapping

### 10.2 Integration Testing
- API endpoint functionality
- Database connection
- Session management
- Authentication flow

### 10.3 UI Testing
- Form validation
- Navigation between pages
- Error message display
- Protected route access

### 10.4 Security Testing
- Password storage verification
- Session security
- CORS policy enforcement
- Input sanitization

---

## 11. Deployment Requirements

### 11.1 Backend Deployment
- Java 17+ runtime
- MySQL 8.0+ database
- Application server (Tomcat embedded in Spring Boot)
- Network connectivity for database

### 11.2 Frontend Deployment
- Modern web browser support
- Node.js for build process
- Static web server for hosting
- Network connectivity to backend API

---

## 12. Future Enhancements

- [ ] JWT token-based authentication
- [ ] OAuth2 social login integration
- [ ] Email verification
- [ ] Password reset functionality
- [ ] Two-factor authentication (2FA)
- [ ] Profile picture upload
- [ ] User profile edit functionality
- [ ] Admin dashboard
- [ ] User activity logging
- [ ] Rate limiting on API endpoints
- [ ] Mobile native applications (iOS/Android)

---

## 13. Revision History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2024-02-09 | Development Team | Initial specification |

---

**Document Status**: Approved  
**Last Updated**: February 9, 2024
