# UML Diagrams
## User Registration and Authentication System

---

## 1. Use Case Diagram

### Overview
The system provides authentication services to end users through web interface.

```
Actors:
- User: Registered or unregistered person
- System: User Authentication System

Primary Use Cases:
1. Register User - Create new account
2. Login - Authenticate user
3. View Profile - Access dashboard
4. Logout - End session
```

### Detailed Use Case Diagram

```
┌──────────────────────────────────────────────┐
│     User Authentication System               │
├──────────────────────────────────────────────┤
│                                              │
│        ┌─────────────────┐                   │
│        │   User          │                   │
│        └────────┬────────┘                   │
│                 │                            │
│        ┌────────┴────────┐                   │
│        │                 │                   │
│        ▼                 ▼                   │
│    ┌────────┐      ┌────────┐                │
│    │Register│      │ Login  │                │
│    └────────┘      └────────┘                │
│        │                 │                   │
│        └────────┬────────┘                   │
│                 ▼                            │
│          ┌─────────────┐                     │
│          │View Profile │                     │
│          └─────────────┘                     │
│                 │                            │
│                 ▼                            │
│           ┌────────────┐                     │
│           │  Logout    │                     │
│           └────────────┘                     │
│                                              │
└──────────────────────────────────────────────┘
```

### Use Case Specifications

#### UC-001: Register User
```
┌─ Use Case: Register User
├─ Actor: Unregistered User
├─ Precondition: User has valid email and username
├─ Main Flow:
│  1. User selects "Register" option
│  2. System displays registration form
│  3. User enters credentials (username, email, password, optional details)
│  4. System validates input
│  5. System checks username and email uniqueness
│  6. System encrypts password with BCrypt
│  7. System saves user to database
│  8. System displays success message
│  9. System navigates to login page
└─ Postcondition: New user account created
```

#### UC-002: Login
```
┌─ Use Case: Login User
├─ Actor: Registered User
├─ Precondition: User account exists
├─ Main Flow:
│  1. User selects "Login" option
│  2. System displays login form
│  3. User enters username and password
│  4. System validates input format
│  5. System finds user by username
│  6. System verifies password against stored hash
│  7. System creates session
│  8. System displays success message
│  9. System navigates to dashboard
└─ Postcondition: User authenticated and logged in
```

#### UC-003: View Profile (Protected)
```
┌─ Use Case: View Profile
├─ Actor: Authenticated User
├─ Precondition: User must be logged in
├─ Main Flow:
│  1. User navigates to "Dashboard"
│  2. System verifies session validity
│  3. System retrieves user information
│  4. System displays user profile page
│  5. User views personal information
└─ Postcondition: Profile information displayed
```

#### UC-004: Logout
```
┌─ Use Case: Logout
├─ Actor: Authenticated User
├─ Precondition: User is logged in
├─ Main Flow:
│  1. User selects "Logout" option
│  2. System invalidates session
│  3. System displays logout confirmation
│  4. System navigates to login page
└─ Postcondition: User session terminated
```

---

## 2. Class Diagram

### Backend Classes

```
┌────────────────────────────────────────┐
│           User Entity                  │
├────────────────────────────────────────┤
│ - id: Long                             │
│ - username: String                     │
│ - email: String                        │
│ - password: String (BCrypt)            │
│ - firstName: String                    │
│ - lastName: String                     │
│ - phoneNumber: String                  │
│ - createdAt: LocalDateTime             │
│ - updatedAt: LocalDateTime             │
├────────────────────────────────────────┤
│ + User()                               │
│ + User(username, email, password)      │
│ + getId(): Long                        │
│ + setId(Long): void                    │
│ + getUsername(): String                │
│ + setUsername(String): void            │
│ + getEmail(): String                   │
│ + setEmail(String): void               │
│ + getPassword(): String                │
│ + setPassword(String): void            │
│ + getFirstName(): String               │
│ + setFirstName(String): void           │
│ + getLastName(): String                │
│ + setLastName(String): void            │
│ + getPhoneNumber(): String             │
│ + setPhoneNumber(String): void         │
│ + getCreatedAt(): LocalDateTime        │
│ + getUpdatedAt(): LocalDateTime        │
│ + onCreate(): void                     │
│ + onUpdate(): void                     │
└────────────────────────────────────────┘
        △
        │ implements
        │
┌────────────────────────────────────────┐
│     JpaRepository Interface            │
│     (Spring Data)                      │
└────────────────────────────────────────┘


┌────────────────────────────────────────┐
│      UserRepository Interface          │
├────────────────────────────────────────┤
│ + save(User): User                     │
│ + findById(Long): Optional<User>       │
│ + delete(User): void                   │
│ + findByUsername(String):              │
│                      Optional<User>    │
│ + findByEmail(String):                 │
│                      Optional<User>    │
│ + existsByUsername(String):            │
│                      boolean           │
│ + existsByEmail(String):               │
│                      boolean           │
└────────────────────────────────────────┘
        △
        │ implements
        │
┌────────────────────────────────────────┐
│    UserRepositoryImpl (Auto-generated  │
│    by Spring Data JPA)                 │
└────────────────────────────────────────┘


┌────────────────────────────────────────┐
│       UserService                      │
├────────────────────────────────────────┤
│ - userRepository: UserRepository       │
│ - passwordEncoder: BCryptPasswordCoder │
├────────────────────────────────────────┤
│ + registerUser(RegisterRequest):       │
│                    UserResponse        │
│ + authenticateUser(username, pwd):     │
│                    UserResponse        │
│ + getUserById(Long):                   │
│                    UserResponse        │
│ + getUserByUsername(String):           │
│                    UserResponse        │
│ - mapToUserResponse(User):             │
│                    UserResponse        │
└────────────────────────────────────────┘
        △
        │ uses
        │
        ├─────────────────────┐
        │                     │
        ▼                     ▼
┌──────────────────┐  ┌──────────────────┐
│ UserRepository   │  │BCryptPasswordCoder│
└──────────────────┘  └──────────────────┘


┌─────────────────────────────────────────┐
│      AuthController (@RestController)   │
├─────────────────────────────────────────┤
│ - userService: UserService              │
├─────────────────────────────────────────┤
│ + register(RegisterRequest):            │
│           ResponseEntity<AuthResponse>  │
│ + login(LoginRequest, HttpSession):     │
│           ResponseEntity<AuthResponse>  │
│ + getCurrentUser(HttpSession):          │
│           ResponseEntity<AuthResponse>  │
│ + logout(HttpSession):                  │
│           ResponseEntity<AuthResponse>  │
├─────────────────────────────────────────┤
│ Endpoints:                              │
│ POST   /api/auth/register               │
│ POST   /api/auth/login                  │
│ GET    /api/auth/me                     │
│ POST   /api/auth/logout                 │
└─────────────────────────────────────────┘
        △
        │ uses
        │
        ▼
  ┌──────────────────┐
  │  UserService     │
  └──────────────────┘


┌─────────────────────────────────────┐
│     DTOs (Data Transfer Objects)    │
├─────────────────────────────────────┤
│                                     │
│ ┌──────────────────────────────┐  │
│ │   RegisterRequest            │  │
│ ├──────────────────────────────┤  │
│ │ - username: String           │  │
│ │ - email: String              │  │
│ │ - password: String           │  │
│ │ - firstName: String          │  │
│ │ - lastName: String           │  │
│ │ - phoneNumber: String        │  │
│ └──────────────────────────────┘  │
│                                     │
│ ┌──────────────────────────────┐  │
│ │   LoginRequest               │  │
│ ├──────────────────────────────┤  │
│ │ - username: String           │  │
│ │ - password: String           │  │
│ └──────────────────────────────┘  │
│                                     │
│ ┌──────────────────────────────┐  │
│ │   UserResponse               │  │
│ ├──────────────────────────────┤  │
│ │ - id: Long                   │  │
│ │ - username: String           │  │
│ │ - email: String              │  │
│ │ - firstName: String          │  │
│ │ - lastName: String           │  │
│ │ - phoneNumber: String        │  │
│ │ - createdAt: LocalDateTime   │  │
│ │ - updatedAt: LocalDateTime   │  │
│ └──────────────────────────────┘  │
│                                     │
│ ┌──────────────────────────────┐  │
│ │   AuthResponse               │  │
│ ├──────────────────────────────┤  │
│ │ - success: Boolean           │  │
│ │ - message: String            │  │
│ │ - user: UserResponse         │  │
│ └──────────────────────────────┘  │
│                                     │
└─────────────────────────────────────┘
```

### Component Relationships

```
┌──────────────────────────────────────────────────────┐
│               Component Diagram                      │
├──────────────────────────────────────────────────────┤
│                                                      │
│  ┌─────────────────┐                                 │
│  │  React Frontend │                                 │
│  │  (Components)   │                                 │
│  └────────┬────────┘                                 │
│           │                                          │
│      HTTP/REST                                       │
│           │                                          │
│           ▼                                          │
│  ┌──────────────────────┐                            │
│  │  Spring Boot Backend │                            │
│  │  ┌────────────────┐  │                            │
│  │  │AuthController  │  │                            │
│  │  └────────┬───────┘  │                            │
│  │           │          │                            │
│  │           ▼          │                            │
│  │  ┌────────────────┐  │                            │
│  │  │ UserService    │  │                            │
│  │  └────────┬───────┘  │                            │
│  │           │          │                            │
│  │           ▼          │                            │
│  │  ┌────────────────┐  │                            │
│  │  │UserRepository  │  │                            │
│  │  └────────┬───────┘  │                            │
│  └───────────┼──────────┘                            │
│              │                                       │
│              ▼                                       │
│  ┌──────────────────────┐                            │
│  │   MySQL Database     │                            │
│  │   - users table      │                            │
│  └──────────────────────┘                            │
│                                                      │
└──────────────────────────────────────────────────────┘
```

---

## 3. Sequence Diagrams

### 3.1 Registration Sequence

```
Actor         Frontend        Backend         Database
  │              │               │                │
  │ 1.Click      │               │                │
  │ Register     │               │                │
  ├─────────────>│               │                │
  │              │               │                │
  │ 2.Show       │               │                │
  │ Form         │               │                │
  │<─────────────┤               │                │
  │              │               │                │
  │ 3.Fill Form  │               │                │
  │ & Submit     │               │                │
  ├─────────────>│               │                │
  │              │ 4.Validate    │                │
  │              │ Input         │                │
  │              │ 5.Check Unique│                │
  │              ├──────────────>│                │
  │              │               │ 6.Query       │
  │              │               ├──────────────>│
  │              │               │                │
  │              │               │ 7.Return      │
  │              │               │ Results       │
  │              │               │<──────────────┤
  │              │               │                │
  │              │ 8.Hash Password                │
  │              │ (BCrypt)                       │
  │              │               │                │
  │              │ 9.Save User   │                │
  │              ├──────────────>│                │
  │              │               │ 10.Insert     │
  │              │               ├──────────────>│
  │              │               │                │
  │              │               │ 11.Confirm    │
  │              │               │<──────────────┤
  │              │               │                │
  │ 12.Success   │ 13.Response   │                │
  │ Message      │<──────────────┤                │
  │<─────────────┤               │                │
  │              │               │                │
  │ 14.Redirect  │               │                │
  │ to Login     │               │                │
  └──────────────────────────────────────────────┘
```

### 3.2 Login Sequence

```
Actor         Frontend        Backend         Database    Session
  │              │               │                │           │
  │ 1.Click      │               │                │           │
  │ Login        │               │                │           │
  ├─────────────>│               │                │           │
  │              │               │                │           │
  │ 2.Show       │               │                │           │
  │ Login Form   │               │                │           │
  │<─────────────┤               │                │           │
  │              │               │                │           │
  │ 3.Enter      │               │                │           │
  │ Credentials  │               │                │           │
  ├─────────────>│               │                │           │
  │              │               │                │           │
  │              │ 4.POST        │                │           │
  │              │ /api/auth/    │                │           │
  │              │ login         │                │           │
  │              ├──────────────>│                │           │
  │              │               │                │           │
  │              │               │ 5.Find by     │           │
  │              │               │ Username      │           │
  │              │               ├──────────────>│           │
  │              │               │                │           │
  │              │               │ 6.Return User │           │
  │              │               │<──────────────┤           │
  │              │               │                │           │
  │              │               │ 7.Verify      │           │
  │              │               │ Password      │           │
  │              │               │ (BCrypt)      │           │
  │              │               │                │           │
  │              │               │ 8.Create      │           │
  │              │               │ Session       ├──────────>│
  │              │               │                │ 9.Store  │
  │              │ 10.      200  │                │ Session  │
  │              │ OK + Data     │                │           │
  │              │<──────────────┤                │           │
  │              │               │                │           │
  │ 11.Success   │               │                │           │
  │<─────────────┤               │                │           │
  │              │               │                │           │
  │ 12.Store     │               │                │           │
  │ Session ID   │               │                │           │
  ├─────────────>│               │                │           │
  │              │               │                │           │
  │ 13.Redirect  │               │                │           │
  │ to Dashboard │               │                │           │
  └──────────────────────────────────────────────────────────┘
```

### 3.3 Get Current User (Protected) Sequence

```
Actor         Frontend        Backend         Database    Session
  │              │               │                │           │
  │ 1.Navigate   │               │                │           │
  │ to Dashboard │               │                │           │
  ├─────────────>│               │                │           │
  │              │               │                │           │
  │              │ 2.GET /api/   │                │           │
  │              │ auth/me       │                │           │
  │              │ + SessionID   │                │           │
  │              ├──────────────>│                │           │
  │              │               │                │           │
  │              │               │ 3.Verify      │           │
  │              │               │ Session       ├──────────>│
  │              │               │                │ 4.Check  │
  │              │               │                │ Validity │
  │              │               │                │           │
  │              │               │                │ 5.Return │
  │              │               │                │ User ID  │
  │              │               │<──────────────┤           │
  │              │               │                │           │
  │              │               │ 6.Get User    │           │
  │              │               │ by ID         │           │
  │              │               ├──────────────>│           │
  │              │               │                │           │
  │              │               │ 7.Return User │           │
  │              │               │ Data          │           │
  │              │               │<──────────────┤           │
  │              │               │                │           │
  │              │ 8.      200   │                │           │
  │              │ OK + User     │                │           │
  │              │<──────────────┤                │           │
  │              │               │                │           │
  │ 9.Display    │               │                │           │
  │ Dashboard    │               │                │           │
  │<─────────────┤               │                │           │
  │              │               │                │           │
  └──────────────────────────────────────────────────────────┘
```

### 3.4 Logout Sequence

```
Actor         Frontend        Backend         Session
  │              │               │                │
  │ 1.Click      │               │                │
  │ Logout       │               │                │
  ├─────────────>│               │                │
  │              │               │                │
  │              │ 2.POST /api/  │                │
  │              │ auth/logout   │                │
  │              │ + SessionID   │                │
  │              ├──────────────>│                │
  │              │               │                │
  │              │               │ 3.Invalidate  │
  │              │               │ Session       ├───────────>
  │              │               │                │ 4.Delete
  │              │ 5.      200   │                │ Session Data
  │              │ OK            │                │
  │              │<──────────────┤                │
  │              │               │                │
  │ 6.Clear      │               │                │
  │ Session      │               │                │
  │<─────────────┤               │                │
  │              │               │                │
  │ 7.Redirect   │               │                │
  │ to Login     │               │                │
  │              │               │                │
  └──────────────────────────────────────────────┘
```

---

## 4. State Diagram

### User State Machine

```
           ┌─────────────────────┐
           │                     │
           ▼                     │
    ╔═════════════╗              │
    ║ Unregistered║              │
    ╚═════════════╝              │
           │                     │
           │ submit registration │
           │ with valid data     │
           │                     │
           ▼                     │
    ╔═════════════╗              │
    ║ Registered  │──────────┐   │
    ║ (Not logged)║          │   │
    ╚═════════════╝          │   │
           ▲                 │   │
           │                 │   │
           │ logout          │   │
           │                 │   │
           │        enter valid credentials
           │                 │   │
           │                 ▼   │
           │          ╔═════════════╗
           │          ║ Logged In   ║
           │          ║ (Authorized)║
           │          ╚═════════════╝
           │                 │
           │                 │
           └─────────────────┘
```

### Authentication State Machine

```
                ┌─────────────────┐
                │ NOT AUTHENTICATED
                └────────┬────────┘
                         │
              submit username/password
              with valid format
                         │
                         ▼
                ┌─────────────────┐
                │  AUTHENTICATING │
                └────────┬────────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
    credentials    credentials      database
    valid?         invalid?         error?
    │              │              │
    ▼              ▼              ▼
   YES            NO           ERROR
    │              │              │
    ▼              ▼              ▼
┌────────┐  ┌─────────╗  ┌──────────────┐
│ Create │  │ REJECTED│  │  FAILED      │
│Session │  └─────────╘  └──────┬───────┘
└────┬───┘                       │
     │                    retry or
     │                    cancel
     ▼                      │
┌──────────────┐            │
│AUTHENTICATED │            │
│(Authorized)  │            │
└────┬─────────┘            │
     │                       │
     │               ┌───────┘
     │               │
     │ logout        │
     ▼ or timeout    ▼
┌─────────────┐ ┌──────────────┐
│ NOT AUTH    │ │ NOT          │
│ (Session    │ │AUTHENTICATED │
│ Ended)      │ │(Error)       │
└─────┬───────┘ └──────┬───────┘
      │                │
      └────────┬───────┘
               ▼
        ┌─────────────┐
        │ NOT         │
        │AUTHENTICATED│
        └─────────────┘
```

---

## 5. Activity Diagram

### Registration Process

```
                    ┌─────────┐
                    │ Start   │
                    └────┬────┘
                         │
                         ▼
                ┌──────────────────┐
                │ Display Form     │
                └────┬─────────────┘
                     │
                     ▼
            ┌──────────────────┐
            │ User Fills Form  │
            └────┬─────────────┘
                 │
                 ▼
        ┌────────────────────┐
        │ Validate Input     │
        └────┬────────┬──────┘
             │        │
         Valid?    Invalid?
             │        │
             │        ▼
             │    ┌─────────────┐
             │    │Show Errors  │
             │    └────┬────────┘
             │         │
             │         ├───────────┐
             │         │           │
             └─────────┴─────┐     │
                             │     │
                             ▼     │
                     ┌─────────────┘
                     │
                     ▼
            ┌──────────────────┐
            │Check Unique      │
            │(Username, Email) │
            └────┬────────┬────┘
                 │        │
            Unique?   Not Unique?
                 │        │
                 │        ▼
                 │    ┌──────────────────┐
                 │    │Show Error Message│
                 │    └──────┬───────────┘
                 │           │
                 └───────────┬┘
                             │
                             ▼
                    ┌──────────────────┐
                    │Hash Password     │
                    │(BCrypt)          │
                    └─────┬────────────┘
                          │
                          ▼
                   ┌──────────────────┐
                   │Save User to DB   │
                   └─────┬────────────┘
                         │
                ┌────────┴────────┐
                │                 │
            Success?          Error?
                │                 │
                ▼                 ▼
        ┌──────────────┐    ┌───────────────┐
        │Show Success  │    │Show Error Msg │
        │Message       │    └───────┬───────┘
        └──────┬───────┘            │
               │        ┌───────────┤
               │        │           │
               ▼        ▼           │
        ┌────────────────────┐     │
        │Redirect to Login   │     │
        └────────┬───────────┘     │
                 │                 │
                 └──────────┬──────┘
                            │
                            ▼
                       ┌──────────┐
                       │ End      │
                       └──────────┘
```

### Login Process

```
                    ┌─────────┐
                    │ Start   │
                    └────┬────┘
                         │
                         ▼
                ┌──────────────────┐
                │ Display Form     │
                └────┬─────────────┘
                     │
                     ▼
            ┌──────────────────┐
            │ Enter Username   │
            │ & Password       │
            └────┬─────────────┘
                 │
                 ▼
        ┌────────────────────┐
        │ Validate Input     │
        └────┬────────┬──────┘
             │        │
         Valid?    Invalid?
             │        │
             ▼        ▼
        Find User ┌──────────────┐
        by        │Show Error Msg│
        Username  └──────┬───────┘
             │           │
             ▼           │
    ┌─────────────────┐  │
    │Found?           │  │
    └────┬────┬───────┘  │
         │    │          │
        YES   NO         │
         │    │          │
         │    ▼          │
         │  ┌──────────────┐
         │  │Show   Error  │
         │  │"User Not     │
         │  │Found"        │
         │  └──────┬───────┘
         │         │
         │         ├─────────┐
         │         │         │
         ▼         ▼         │
    ┌──────────────┐        │
    │Verify        │        │
    │Password      │        │
    │(BCrypt)      │        │
    └────┬────┬────┘        │
         │    │             │
     Match?  Match?         │
         │    │             │
        YES   NO            │
         │    │             │
         │    ▼             │
         │ ┌─────────────┐  │
         │ │Show Error   │  │
         │ │"Invalid     │  │
         │ │Password"    │  │
         │ └────┬────────┘  │
         │      │           │
         └──┬───┴───────────┘
            │
            ▼
    ┌─────────────────┐
    │Create Session   │
    └─────┬───────────┘
          │
          ▼
    ┌─────────────────┐
    │Set Session ID   │
    │in Cookie        │
    └─────┬───────────┘
          │
          ▼
    ┌─────────────────┐
    │Redirect to      │
    │Dashboard        │
    └─────┬───────────┘
          │
          ▼
       ┌─────────┐
       │ End     │
       └─────────┘
```

---

## 6. Deployment Diagram

### System Architecture Deployment

```
┌───────────────────────────────────────────────────────┐
│          Production Deployment Architecture          │
├───────────────────────────────────────────────────────┤
│                                                       │
│  ┌─────────────────────────────────────────────────┐ │
│  │         Client Tier (User Devices)              │ │
│  │  ┌─────────────────────────────────────────┐  │ │
│  │  │  Browser (Chrome, Fire fox, Safari)     │  │ │
│  │  │  - React Application                    │  │ │
│  │  │  - Sessions & Cookies                   │  │ │
│  │  └─────────────────────┬───────────────────┘  │ │
│  └────────────────────────┼────────────────────────┘ │
│                           │ HTTP/HTTPS              │
│                           │                         │
│  ┌────────────────────────▼─────────────────────┐   │
│  │         Web Tier (Cloud/On-Premise)          │   │
│  │  ┌────────────────────────────────────────┐ │   │
│  │  │   Java Application Server               │ │   │
│  │  │   (Tomcat - Embedded in Spring Boot)   │ │   │
│  │  │                                        │ │   │
│  │  │   ┌────────────────────────────────┐  │ │   │
│  │  │   │   Spring Boot Application      │  │ │   │
│  │  │   │   - AuthController             │  │ │   │
│  │  │   │   - UserService                │  │ │   │
│  │  │   │   - Security Config            │  │ │   │
│  │  │   └──────────────┬─────────────────┘  │ │   │
│  │  └──────────────────┼────────────────────┘ │   │
│  └─────────────────────┼─────────────────────── ┘   │
│                        │ JDBC Connection         │
│  ┌─────────────────────▼─────────────────────────┐  │
│  │       Database Tier                           │  │
│  │  ┌──────────────────────────────────────────┐ │  │
│  │  │      MySQL Database Server (8.0+)        │ │  │
│  │  │                                          │ │  │
│  │  │      ┌───────────────────────────────┐  │ │  │
│  │  │      │  userauth_db database         │  │ │  │
│  │  │      │                               │  │ │  │
│  │  │      │  users table                  │  │ │  │
│  │  │      │  - id (Primary Key)           │  │ │  │
│  │  │      │  - username (UNIQUE)          │  │ │  │
│  │  │      │  - email (UNIQUE)             │  │ │  │
│  │  │      │  - password (BCrypt)          │  │ │  │
│  │  │      │  - first_name, last_name      │  │ │  │
│  │  │      │  - phone_number               │  │ │  │
│  │  │      │  - created_at, updated_at     │  │ │  │
│  │  │      └───────────────────────────────┘  │ │  │
│  │  └──────────────────────────────────────────┘ │  │
│  └──────────────────────────────────────────────┘  │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### Physical Architecture

```
┌──────────────────────────────────────────┐
│      CI/CD Pipeline (GitHub Actions)     │
│                                          │
│  1. Code Push                            │
│  2. Automated Tests                      │
│  3. Build Docker Image                   │
│  4. Push to Registry                     │
│  5. Deploy to Server                     │
└──────────────────────────────────────────┘
             │
             ▼
┌──────────────────────────────────────────┐
│        Docker Containers                 │
│                                          │
│  ┌────────────────────────────────────┐ │
│  │  Container: Frontend Service       │ │
│  │  - Node.js Runtime                 │ │
│  │  - React Production Build          │ │
│  │  - Port: 3000                      │ │
│  └────────────────────────────────────┘ │
│                                          │
│  ┌────────────────────────────────────┐ │
│  │  Container: Backend Service        │ │
│  │  - Java 17 Runtime                 │ │
│  │  - Spring Boot JAR                 │ │
│  │  - Port: 8080                      │ │
│  └────────────────────────────────────┘ │
│                                          │
│  ┌────────────────────────────────────┐ │
│  │  Container: Database Service       │ │
│  │  - MySQL Server 8.0                │ │
│  │  - Port: 3306                      │ │
│  │  - Persistent Volume               │ │
│  └────────────────────────────────────┘ │
└──────────────────────────────────────────┘
             │
             ▼
┌──────────────────────────────────────────┐
│      Host/Cloud Provider                 │
│  (AWS EC2, Azure VM, DigitalOcean, etc.)│
└──────────────────────────────────────────┘
```

---

## 7. Object Diagram (Instance Example)

### Runtime Instance

```
Object Instances:

user1 : User
┌────────────────────────────┐
│ id = 1                     │
│ username = "john_doe"      │
│ email = "john@example.com" │
│ password = "$2a$10$..."    │
│ firstName = "John"         │
│ lastName = "Doe"           │
│ phoneNumber = "+1234567890"│
│ createdAt = 2024-02-09 ... │
│ updatedAt = 2024-02-09 ... │
└────────────────────────────┘

session1 : HttpSession
┌────────────────────────────┐
│ userId = 1                 │
│ username = "john_doe"      │
│ createdAt = 2024-02-09 ... │
│ lastAccessedTime = ...     │
└────────────────────────────┘

registerRequest1 : RegisterRequest
┌────────────────────────────┐
│ username = "jane_smith"    │
│ email = "jane@example.com" │
│ password = "SecurePass123" │
│ firstName = "Jane"         │
│ lastName = "Smith"         │
│ phoneNumber = null         │
└────────────────────────────┘

authResponse1 : AuthResponse
┌────────────────────────────┐
│ success = true             │
│ message = "Login success"  │
│ user → user1 : User        │
└────────────────────────────┘
```

---

## Summary

This UML documentation provides comprehensive views of the system architecture including:

- ✅ Use Cases - Primary interactions with system
- ✅ Class Diagrams - Object structure and relationships
- ✅ Sequence Diagrams - Interaction sequences
- ✅ State Diagrams - State transitions
- ✅ Activity Diagrams - Process flows
- ✅ Deployment Diagram - Physical architecture
- ✅ Object Diagrams - Runtime instances

**Document Status**: ✅ Complete  
**Last Updated**: February 9, 2024  
**Version**: 1.0

---
