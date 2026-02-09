# Entity-Relationship Diagram (ERD)
## User Registration and Authentication System

---

## 1. Database Overview

The system uses a single primary entity table for user storage, with database normalization applied. The database is designed for scalability and maintainability.

---

## 2. Entity-Relationship Diagram

### ERD Visualization

```
┌─────────────────────────────────────┐
│            USERS TABLE              │
├─────────────────────────────────────┤
│ Column Name      │ Type    │ Constraints │
├──────────────────┼─────────┼─────────────┤
│ id               │ BIGINT  │ PK, AUTO   │
│ username         │ VARCHAR │ NOT NULL   │
│                  │ (255)   │ UNIQUE     │
├──────────────────┼─────────┼─────────────┤
│ email            │ VARCHAR │ NOT NULL   │
│                  │ (255)   │ UNIQUE     │
├──────────────────┼─────────┼─────────────┤
│ password         │ VARCHAR │ NOT NULL   │
│                  │ (255)   │            │
├──────────────────┼─────────┼─────────────┤
│ first_name       │ VARCHAR │ NULL       │
│                  │ (255)   │            │
├──────────────────┼─────────┼─────────────┤
│ last_name        │ VARCHAR │ NULL       │
│                  │ (255)   │            │
├──────────────────┼─────────┼─────────────┤
│ phone_number     │ VARCHAR │ NULL       │
│                  │ (20)    │            │
├──────────────────┼─────────┼─────────────┤
│ created_at       │ TIMESTAMP│ NOT NULL   │
│                  │         │ DEFAULT NOW│
├──────────────────┼─────────┼─────────────┤
│ updated_at       │ TIMESTAMP│ NOT NULL   │
│                  │         │ DEFAULT NOW│
│                  │         │ ON UPDATE  │
└─────────────────────────────────────┘
```

---

## 3. Table Definition (SQL)

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
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email)
);
```

---

## 4. Attribute Descriptions

### 4.1 id (Primary Key)
- **Type**: BIGINT
- **Constraints**: PRIMARY KEY, AUTO_INCREMENT
- **Description**: Unique identifier for each user
- **Purpose**: Uniquely identifies a user in the system
- **Range**: 1 to 9,223,372,036,854,775,807

### 4.2 username
- **Type**: VARCHAR(255)
- **Constraints**: NOT NULL, UNIQUE
- **Description**: User's login username
- **Purpose**: Used for authentication and identification
- **Validation**: 3-50 characters, alphanumeric and underscores
- **Example**: `john_doe_123`

### 4.3 email
- **Type**: VARCHAR(255)
- **Constraints**: NOT NULL, UNIQUE
- **Description**: User's email address
- **Purpose**: Communication and account recovery
- **Validation**: Must follow valid email format
- **Example**: `john.doe@example.com`

### 4.4 password
- **Type**: VARCHAR(255)
- **Constraints**: NOT NULL
- **Description**: Encrypted user password using BCrypt
- **Purpose**: Authentication credential
- **Storage**: Always encrypted with BCrypt hash (60 characters minimum)
- **Note**: Never stored in plain text
- **Example**: `$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2uFm2`

### 4.5 first_name
- **Type**: VARCHAR(255)
- **Constraints**: NULLABLE
- **Description**: User's first name
- **Purpose**: Personal profile information
- **Optional**: Users can leave this blank
- **Example**: `John`

### 4.6 last_name
- **Type**: VARCHAR(255)
- **Constraints**: NULLABLE
- **Description**: User's last name
- **Purpose**: Personal profile information
- **Optional**: Users can leave this blank
- **Example**: `Doe`

### 4.7 phone_number
- **Type**: VARCHAR(20)
- **Constraints**: NULLABLE
- **Description**: User's contact phone number
- **Purpose**: Communication
- **Optional**: Users can leave this blank
- **Validation**: Various formats supported
- **Example**: `+1-555-123-4567`

### 4.8 created_at
- **Type**: TIMESTAMP
- **Constraints**: NOT NULL, DEFAULT CURRENT_TIMESTAMP
- **Description**: Account creation timestamp
- **Purpose**: Audit trail and user history
- **Automatic**: Set automatically when record is created
- **Format**: YYYY-MM-DD HH:MM:SS
- **Example**: `2024-02-09 10:30:45`

### 4.9 updated_at
- **Type**: TIMESTAMP
- **Constraints**: NOT NULL, DEFAULT CURRENT_TIMESTAMP, ON UPDATE CURRENT_TIMESTAMP
- **Description**: Last update timestamp
- **Purpose**: Audit trail and data freshness
- **Automatic**: Updated automatically on any record modification
- **Format**: YYYY-MM-DD HH:MM:SS
- **Example**: `2024-02-09 14:15:32`

---

## 5. Keys and Constraints

### 5.1 Primary Key (PK)
- **Column**: id
- **Type**: BIGINT AUTO_INCREMENT
- **Purpose**: Unique identification of users
- **Indexing**: Automatically indexed

### 5.2 Unique Constraints (UQ)
- **username**: Ensures no duplicate usernames in system
- **email**: Ensures no duplicate email addresses in system
- **Enforcement**: Database-level constraint prevents duplicates

### 5.3 Not Null Constraints
- **id**: Always required
- **username**: Required for authentication
- **email**: Required for validation
- **password**: Required for authentication
- **created_at**: Required for audit
- **updated_at**: Required for audit

### 5.4 Indexes
```sql
-- Primary Key Index (Automatic)
SHOW INDEXES FROM users WHERE Column_name = 'id';

-- Unique Indexes (Automatic)
SHOW INDEXES FROM users WHERE Column_name IN ('username', 'email');

-- Optional Performance Indexes
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
```

---

## 6. Relationships and Cardinality

### Current Design (Normalized)
```
USERS
  ├─ One user has ONE account
  └─ Each user identified by unique ID
```

### Future Enhancements
```
Potential relationships (not implemented in Phase 1):

USERS (1) ──── (*) ROLES
    │
    └── (*) SESSIONS
    │
    └── (*) AUDIT_LOGS
    │
    └── (*) VERIFICATION_TOKENS
```

---

## 7. Data Integrity

### 7.1 Referential Integrity
- No foreign keys in Phase 1
- Username and Email uniqueness enforced at database level

### 7.2 Data Validation Rules

| Field | Validation Rule |
|-------|-----------------|
| username | 3-50 chars, alphanumeric + underscore |
| email | Valid RFC 5322 email format |
| password | Min 8 chars, stored as BCrypt hash |
| first_name | 0-255 chars, alphabetic + space |
| last_name | 0-255 chars, alphabetic + space |
| phone_number | 7-20 digits with optional formatting |

---

## 8. Normalization

### Normal Forms Applied

#### First Normal Form (1NF)
✅ Atomic values only - every field contains a single value
- No repeating groups
- Example: phone_number is single value (not comma-separated list)

#### Second Normal Form (2NF)
✅ All attributes depend on the entire primary key
- Single primary key (id)
- All non-key attributes functionally dependent on id

#### Third Normal Form (3NF)
✅ No transitive dependencies
- Non-key attributes depend only on primary key
- No non-key attribute depends on another non-key attribute

#### Boyce-Codd Normal Form (BCNF)
✅ Every determinant is a candidate key
- username and email could be candidate keys
- Maintained by unique constraints

---

## 9. Sample Data

### Example Records

```sql
INSERT INTO users 
(username, email, password, first_name, last_name, phone_number, created_at, updated_at)
VALUES 
(
    'john_doe',
    'john@example.com',
    '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy2uFm2',
    'John',
    'Doe',
    '+1-555-123-4567',
    '2024-02-09 10:30:45',
    '2024-02-09 10:30:45'
);

INSERT INTO users 
(username, email, password, first_name, last_name, created_at, updated_at)
VALUES 
(
    'jane_smith',
    'jane@example.com',
    '$2a$10$KIX6D29wEGW8x7jIfmyYfe7X6BVy9B8VbLBCXVZGrZ5K5LOpZBgzG',
    'Jane',
    'Smith',
    '2024-02-09 11:15:20',
    '2024-02-09 11:15:20'
);
```

---

## 10. Database Performance

### 10.1 Indexes
```sql
-- Automatic indexes
SHOW INDEXES FROM users;

-- Query execution plan
EXPLAIN SELECT * FROM users WHERE username = 'john_doe';
```

### 10.2 Query Optimization

| Operation | Query | Index Used |
|-----------|-------|-----------|
| Find by username | `SELECT * FROM users WHERE username = ?` | idx_username |
| Find by email | `SELECT * FROM users WHERE email = ?` | idx_email |
| Insert new user | `INSERT INTO users ...` | PRIMARY KEY |
| Update user | `UPDATE users WHERE id = ?` | PRIMARY KEY |
| Delete user | `DELETE FROM users WHERE id = ?` | PRIMARY KEY |

### 10.3 Performance Metrics
- Average query response time: < 10ms
- Connection pool size: 10
- Max connections: 100
- Transaction isolation: READ_COMMITTED

---

## 11. Backup and Recovery

### 11.1 Backup Strategy
```bash
# MySQL Backup Command
mysqldump -u root -p userauth_db > userauth_backup.sql

# Restore Command
mysql -u root -p userauth_db < userauth_backup.sql
```

### 11.2 Disaster Recovery
- Regular backups every 4 hours
- Transaction logs enabled
- Point-in-time recovery capability
- Database replication ready (for future)

---

## 12. Security Considerations

### 12.1 Data Protection
- Passwords encrypted with BCrypt (salt rounds: 10)
- PII (personally identifiable information) in plain text
- Consider encryption at rest for future
- Consider PII anonymization policies

### 12.2 Access Control
- Database credentials not hardcoded
- Environment variables for sensitive data
- SQL injection prevention via ORM (JPA)
- Prepared statements used for all queries

### 12.3 Audit Trail
- `created_at` field tracks creation
- `updated_at` field tracks modifications
- Future: Implementation of audit log table

---

## 13. Migration Scripts

### 13.1 Create Database
```sql
CREATE DATABASE userauth_db;
USE userauth_db;
```

### 13.2 Create Users Table
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
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP
);
```

### 13.2 Add Indexes (Optional)
```sql
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
```

---

## 14. Schema Evolution

### 14.1 Version 1.0 (Current)
- Single users table
- Minimal fields for MVP
- Basic audit timestamps

### 14.2 Version 1.1 (Future Enhancements)
```sql
-- Add profile picture URL
ALTER TABLE users ADD COLUMN profile_picture_url VARCHAR(500);

-- Add email verification
ALTER TABLE users ADD COLUMN is_email_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE users ADD COLUMN email_verified_at TIMESTAMP;

-- Add login tracking
ALTER TABLE users ADD COLUMN last_login_at TIMESTAMP;

-- Add user status
ALTER TABLE users ADD COLUMN status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE';
```

### 14.3 Version 2.0 (Major Enhancement)
```sql
-- Create roles table
CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Create user_roles junction table
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Create audit log table
CREATE TABLE audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    action VARCHAR(50),
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## 15. Database Maintenance

### 15.1 Regular Maintenance
```sql
-- Check table statistics
ANALYZE TABLE users;

-- Optimize table
OPTIMIZE TABLE users;

-- Repair table (if needed)
REPAIR TABLE users;
```

### 15.2 Monitoring
```sql
-- Check table size
SELECT 
    table_name,
    round(((data_length + index_length) / 1024 / 1024), 2) AS size_mb
FROM information_schema.tables
WHERE table_schema = 'userauth_db';

-- Check row count
SELECT COUNT(*) FROM users;
```

---

## 16. Constraints Justification

### Why These Constraints?

1. **Username NOT NULL & UNIQUE**
   - Required for login functionality
   - Prevents duplicate usernames
   - Ensures unique identification

2. **Email NOT NULL & UNIQUE**
   - Required for account verification
   - Prevents duplicate registrations
   - Needed for password recovery features

3. **Password NOT NULL**
   - Essential authentication credential
   - Cannot be null for security

4. **Created_at & Updated_at**
   - Audit trail for compliance
   - User activity tracking
   - Data freshness tracking

---

## 17. Conclusion

The ERD for this system follows database normalization best practices with a single-table design suitable for Phase 1 implementation. The schema is designed to be scalable and maintainable, with provisions for future enhancements without major structural changes.

**Database Status**: ✅ Production Ready  
**Last Updated**: February 9, 2024  
**Version**: 1.0

---
