-- ============================================================================
-- Cinema Management System – User, Role & Permissions Schema (PostgreSQL)
-- ============================================================================

-- 1. Tables: Authorities (Roles), Users, and Many-to-Many Relationship
-- ----------------------------------------------------------------------------

-- Table for system roles or permissions
CREATE TABLE auth_authorities (
  authority_id INTEGER PRIMARY KEY,
  authority_name VARCHAR(100) NOT NULL UNIQUE
);

-- Table for users (administrators, managers, employees)
CREATE TABLE auth_users (
  user_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  hashed_password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Many-to-Many relationship between users and their authorities
CREATE TABLE auth_user_authorities (
  user_id INTEGER NOT NULL,
  authority_id INTEGER NOT NULL,
  PRIMARY KEY (user_id, authority_id),
  FOREIGN KEY (user_id) REFERENCES auth_users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (authority_id) REFERENCES auth_authorities(authority_id) ON DELETE CASCADE
);

-- ============================================================================
-- 2. Cleanup (Useful for Development Reset)
-- ============================================================================
DELETE FROM auth_user_authorities;
DELETE FROM auth_users;
DELETE FROM auth_authorities;

-- ============================================================================
-- 3. Insert Authorities (Roles & Permissions)
-- ============================================================================
INSERT INTO auth_authorities (authority_id, authority_name) VALUES
(1,'ADMIN'),
--(2,'CINEMA_MANAGER'),
--(3,'CINEMA_VIEW'),
--(4,'CINEMA_CREATE'),
--(5,'CINEMA_UPDATE'),
--(6,'MOVIE_MANAGE'),
--(7,'SHOWTIME_MANAGE'),
--(8,'CUSTOMER_VIEW'),
--(9,'CUSTOMER_EDIT'),
--(10,'TICKET_SELL'),
--(11,'RESERVATION_VIEW'),
--(12,'RESERVATION_CANCEL');

-- ============================================================================
-- 4. Insert Users
-- Password: 123456 (BCrypt hashed)
-- ============================================================================
INSERT INTO auth_users (username, hashed_password, full_name) VALUES
('admin', '$2a$10$e6Ijw.dlWTn8KkEsbF3w9uDgNGFSz/gXV3BVrR/LBElZOmOAtRL4S', 'Main Administrator'),
('cinema_manager_01', '$2a$10$e6Ijw.dlWTn8KkEsbF3w9uDgNGFSz/gXV3BVrR/LBElZOmOAtRL4S', 'Lima Cinema Manager'),
('cinema_employee_01', '$2a$10$e6Ijw.dlWTn8KkEsbF3w9uDgNGFSz/gXV3BVrR/LBElZOmOAtRL4S', 'Employee - Miraflores');

-- ============================================================================
-- 5. Assign Authorities to Users
-- ============================================================================
-- Admin has all permissions
INSERT INTO auth_user_authorities (user_id, authority_id)
SELECT 1, authority_id FROM auth_authorities;

-- Cinema Manager permissions
INSERT INTO auth_user_authorities (user_id, authority_id)
SELECT 2, authority_id FROM auth_authorities
WHERE authority_name IN (
  'CINEMA_MANAGER', 'CINEMA_VIEW', 'MOVIE_MANAGE', 'SHOWTIME_MANAGE', 'RESERVATION_VIEW'
);

-- Employee permissions
INSERT INTO auth_user_authorities (user_id, authority_id)
SELECT 3, authority_id FROM auth_authorities
WHERE authority_name IN (
  'TICKET_SELL', 'CUSTOMER_VIEW', 'RESERVATION_VIEW', 'RESERVATION_CANCEL'
);

-- ============================================================================
-- 6. View: User and Their Authorities
-- ============================================================================
CREATE OR REPLACE VIEW view_user_authorities AS
SELECT
  u.user_id,
  u.username,
  u.full_name,
  a.authority_id,
  a.authority_name
FROM auth_users u
JOIN auth_user_authorities ua ON u.user_id = ua.user_id
JOIN auth_authorities a ON ua.authority_id = a.authority_id
ORDER BY u.user_id, a.authority_id;

-- Sample view query
-- SELECT * FROM view_user_authorities;

-- ============================================================================
-- 7. SELECT Queries
-- ============================================================================

SELECT * FROM auth_authorities
SELECT * FROM auth_users;
SELECT * FROM auth_user_authorities;
