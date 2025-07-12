-- ============================================================================
-- Complete Business Tables Schema for Cinema Management System (PostgreSQL)
-- ============================================================================

-- ============================================================================
-- 1.1. Tables: Authorities (Roles), Users, and Many-to-Many Relationship
-- ============================================================================

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
-- 1.2. Cleanup (Useful for Development Reset)
-- ============================================================================

DELETE FROM auth_user_authorities;
DELETE FROM auth_users;
DELETE FROM auth_authorities;

-- ============================================================================
-- 1.3. Insert Authorities (Roles & Permissions)
-- ============================================================================

INSERT INTO auth_authorities (authority_id, authority_name) VALUES
(1, 'ADMIN'),
(2, 'CUSTOMER');
--(2,'CINEMA_MANAGER'),
--(3,'CINEMA_VIEW'),
--(4,'CINEMA_CREATE'),
--(5,'CINEMA_UPDATE');

-- ============================================================================
-- 1.4. Insert Users
-- Password: 123456 (BCrypt hashed)
-- ============================================================================

INSERT INTO auth_users (username, hashed_password, full_name) VALUES
('admin', '$2a$10$e6Ijw.dlWTn8KkEsbF3w9uDgNGFSz/gXV3BVrR/LBElZOmOAtRL4S', 'Main Administrator'),
--('cinema_manager_01', '$2a$10$e6Ijw.dlWTn8KkEsbF3w9uDgNGFSz/gXV3BVrR/LBElZOmOAtRL4S', 'Lima Cinema Manager'),
--('cinema_employee_01', '$2a$10$e6Ijw.dlWTn8KkEsbF3w9uDgNGFSz/gXV3BVrR/LBElZOmOAtRL4S', 'Employee - Miraflores');

-- ============================================================================
-- 1.5. Assign Authorities to Users
-- ============================================================================
-- Admin has all permissions
INSERT INTO auth_user_authorities (user_id, authority_id)
SELECT 1, authority_id FROM auth_authorities;

-- Cinema Manager permissions
--INSERT INTO auth_user_authorities (user_id, authority_id)
--SELECT 2, authority_id FROM auth_authorities
--WHERE authority_name IN (
--  'CINEMA_MANAGER', 'CINEMA_VIEW', 'MOVIE_MANAGE', 'SHOWTIME_MANAGE', 'RESERVATION_VIEW'
--);

-- ============================================================================
-- 1.6. SELECT Queries
-- ============================================================================

SELECT * FROM auth_authorities;
SELECT * FROM auth_users;
SELECT * FROM auth_user_authorities;

-- ============================================================================
-- 2.1. Tables creation: Departments, Provinces, Districts
-- ============================================================================

-- Table: departments (Peruvian departments)
CREATE TABLE departments (
    department_id CHAR(2) PRIMARY KEY,
    department_name VARCHAR(20) NOT NULL
);

-- Table: provinces (linked to departments)
CREATE TABLE provinces (
    province_id CHAR(4) PRIMARY KEY,
    province_name VARCHAR(30) NOT NULL,
    department_id CHAR(2) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE CASCADE
);

-- Table: districts (linked to provinces)
CREATE TABLE districts (
    district_id CHAR(6) PRIMARY KEY,
    district_name VARCHAR(50) NOT NULL,
    province_id CHAR(4) NOT NULL,
    FOREIGN KEY (province_id) REFERENCES provinces(province_id) ON DELETE CASCADE
);

-- ============================================================================
-- 2.2. DATA CLEANUP (USEFUL DURING DEVELOPMENT RESET)
-- ============================================================================

-- Deletes records from authentication tables if previously inserted
DELETE FROM auth_user_authorities;
DELETE FROM auth_users;
DELETE FROM auth_authorities;

-- ============================================================================
-- 2.3. DATA INSERTION – UBIGEO DATASET (Partial)
-- ============================================================================

-- -----------------------------------------
-- Insert Departments (12 total)
-- -----------------------------------------

INSERT INTO departments (department_id, department_name) VALUES
('04','AREQUIPA'),
('06','CAJAMARCA'),
('08','CUSCO'),
('10','HUANUCO'),
('12','JUNIN'),
('13','LA LIBERTAD'),
('14','LAMBAYEQUE'),
('15','LIMA'),
('20','PIURA'),
('21','PUNO'),
('23','TACNA'),
('25','UCAYALI');

-- Check inserted departments
SELECT * FROM departments


-- -----------------------------------------
-- Insert Provinces (1+ per department)
-- -----------------------------------------

INSERT INTO provinces (province_id, province_name, department_id) VALUES
('0401','AREQUIPA','04'),
('0601','CAJAMARCA','06'),
('0801','CUSCO','08'),
('1001','HUANUCO','10'),
('1201','HUANCAYO','12'),
('1301','TRUJILLO','13'),
('1401','CHICLAYO','14'),
('1501','LIMA','15'),
('2001','PIURA','20'),
('2101','PUNO','21'),
('2111','SAN ROMAN','21'),
('2301','TACNA','23'),
('2501','CORONEL PORTILLO','25');

-- Check inserted provinces
SELECT * FROM provinces

-- ALSO TO DEVELOP
INSERT INTO districts (district_id, district_name, province_id) VALUES
('150114','LA MOLINA','1501'),
('150122','MIRAFLORES','1501'),
('150131','SAN ISIDRO','1501');


-- -----------------------------------------
-- Insert Districts (linked to provinces)
-- -----------------------------------------

INSERT INTO districts (district_id, district_name, province_id) VALUES
('040101','AREQUIPA','0401'),
('060101','CAJAMARCA','0601'),
('080101','CUSCO','0801'),
('100101','HUANUCO','1001'),
('120101','HUANCAYO','1201'),
('130101','TRUJILLO','1301'),
('140101','CHICLAYO','1401'),
('150101','LIMA','1501'),
('200101','PIURA','2001'),
('210101','PUNO','2101'),
('211101','JULIACA','2111'),
('230101','TACNA','2301'),
('250101','CALLERIA','2501');

-- Check inserted districts
SELECT * FROM districts

-- ============================================================================
-- 2.4. FINAL QUERY – COMPLETE UBIGEO VIEW
-- ============================================================================

-- Returns: UBIGEO code (6 digits), department, province, and district names
SELECT
    d.district_id AS ubigeo,
    dep.department_name,
    prov.province_name,
    d.district_name
FROM districts d
JOIN provinces prov ON d.province_id = prov.province_id
JOIN departments dep ON prov.department_id = dep.department_id
ORDER BY d.district_id;

-- ============================================================================
-- 3.1. Table creation: cinemas
-- ============================================================================

CREATE TABLE cinemas (
    id UUID PRIMARY KEY,
    district_id CHAR(6) NOT NULL,
    cinema_name VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone_number CHAR(9),
    contact_email VARCHAR(100),
    image_url TEXT,
    status VARCHAR(20) NOT NULL,-- CHECK (status IN ('ACTIVE', 'INACTIVE', 'MAINTENANCE', 'SUSPENDED', 'COMING_SOON')),
    opening_date DATE,
    opening_time TIME,
    closing_time TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (district_id) REFERENCES districts(district_id) ON DELETE CASCADE
);

-- ============================================================================
-- 1.2. Cleanup (Useful for Development Reset)
-- ============================================================================

DELETE FROM cinemas;

-- ============================================================================
-- 3.3. DATA INSERTION – CINEMA
-- ============================================================================

INSERT INTO cinemas (id, district_id, cinema_name, address, phone_number, contact_email, image_url, status, opening_date, opening_time, closing_time) VALUES
(gen_random_uuid(), '040101', 'Cinemagic Arequipa', 'Av. Ejercito 234', '960346752', 'contacto.arequipa@cinemagic.pe', 'https://i.ibb.co/DqFxKMj/cm-arequipa.png', 'ACTIVE', '2002-04-23', '13:30:00', '21:30:00'),
(gen_random_uuid(), '060101', 'Cinemagic Cajamarca', 'Av. Via de Evitamiento Norte', '960783460', 'contacto.cajamarca@cinemagic.pe', 'https://i.ibb.co/Ldjcv7NK/cm-cajamarca.png', 'ACTIVE', '2014-05-12', '14:30:00', '22:45:00'),
(gen_random_uuid(), '080101', 'Cinemagic Cusco', 'Av. Collasuyo 2315 Lote A', '960437810', 'contacto.cusco@cinemagic.pe', 'https://i.ibb.co/LhpMQJb1/cm-cusco.png', 'ACTIVE', '2014-06-24', '12:50:00', '23:40:00'),
(gen_random_uuid(), '100101', 'Cinemagic Huanuco', 'Jr. Independencia 462', '960197608', 'contacto.huanuco@cinemagic.pe', 'https://i.ibb.co/8yNC5Z8/cm-huanuco.png', 'ACTIVE', '2013-06-12', '14:15:00', '22:10:00'),
(gen_random_uuid(), '120101', 'Cinemagic Huancayo', 'Av. Ferrocarril 843 - Junin', '960864439', 'contacto.huancayo@cinemagic.pe', 'https://i.ibb.co/kV3p259H/cm-huancayo.png', 'ACTIVE', '2008-12-15', '16:10:00', '22:50:00'),
(gen_random_uuid(), '130101', 'Cinemagic Trujillo', 'Calle Orbegoso 340 - La Libertad', '960159753', 'contacto.trujillo@cinemagic.pe', 'https://i.ibb.co/WvvG2rjZ/cm-trujillo.png', 'ACTIVE','2007-11-08', '15:30:00', '22:30:00'),
(gen_random_uuid(), '140101', 'Cinemagic Chiclayo', 'Av. Miguel de Cervantes 543 - Lambayeque', '960739288', 'contacto.chiclayo@cinemagic.pe', 'https://i.ibb.co/r1L32G6/cm-chiclayo.png', 'ACTIVE', '2005-12-08', '14:20:00', '23:40:00'),
(gen_random_uuid(), '150101', 'Cinemagic Lima', 'Jr. De la Unión 367', '960693007', 'contacto.lima@cinemagic.pe', 'https://i.ibb.co/gZLZ59Rd/cm-lima.png', 'ACTIVE', '1998-07-22', '12:40:00', '23:50:00'),
(gen_random_uuid(), '200101', 'Cinemagic Piura', 'Jr. Huancavelica 843', '960496673', 'contacto.piura@cinemagic.pe', 'https://i.ibb.co/jZJC7qkg/cm-piura.png', 'ACTIVE', '2011-01-15', '13:30:00', '21:45:00'),
(gen_random_uuid(), '210101', 'Cinemagic Puno', 'Av. Los Incas 1956', '960482297', 'contacto.puno@cinemagic.pe', 'https://i.ibb.co/zhXx47GZ/cm-puno.png', 'ACTIVE', '2009-04-21', '12:30:00', '23:40:00'),
(gen_random_uuid(), '211101', 'Cinemagic Juliaca', 'Calle Tumbes 463 - Puno', '960437095', 'contacto.juliaca@cinemagic.pe', 'https://i.ibb.co/FLb4JJ1P/cm-juliaca.png', 'INACTIVE', '2003-10-07', '15:10:00', '23:00:00'),
(gen_random_uuid(), '230101', 'Cinemagic Tacna', 'Calle Cuzco 362', '960761490', 'contacto.tacna@cinemagic.pe', 'https://i.ibb.co/bjzJc2BS/cm-tacna.png', 'MAINTENANCE', '2008-03-18', '12:50:00', '22:20:00'),
(gen_random_uuid(), '250101', 'Cinemagic Pucallpa', 'Av.Centenario 529 - Ucayali', '960793488', 'contacto.ucayali@cinemagic.pe', 'https://i.ibb.co/DgszHhJ1/cm-calleria.png', 'ACTIVE', '2007-09-27', '14:20:00', '23:15:00');


SELECT * FROM cinemas;


-- ============================================================================
-- 4.1. Table creation: customers
-- ============================================================================

CREATE TABLE customers (
    id UUID PRIMARY KEY,
    district_id CHAR(6) NOT NULL,
    cinema_id UUID NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
	login_email VARCHAR(100) NOT NULL,
	login_password VARCHAR(100) NOT NULL,
    document_type VARCHAR(20) NOT NULL,
    document_number VARCHAR(12) NOT NULL UNIQUE,
    birth_date DATE,
    phone_number CHAR(9),
    gender CHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	CONSTRAINT fk_district
        FOREIGN KEY (district_id)
        REFERENCES districts(district_id)
        ON DELETE CASCADE,

	CONSTRAINT fk_cinema
        FOREIGN KEY (cinema_id)
        REFERENCES cinemas(id)
        ON DELETE CASCADE
);


-- ============================================================================
-- 4.2. Cleanup (Useful for Development Reset)
-- ============================================================================

DELETE FROM customers;

-- ============================================================================
-- 4.3. DATA INSERTION – Customer
-- ============================================================================

INSERT INTO customers (id, district_id, cinema_id, first_name, last_name, login_email, login_password, document_type, document_number, birth_date, phone_number, gender) VALUES
(gen_random_uuid(), '150101', 'ef69fb3d-d4a7-453b-be46-aae44291adf3', 'TOM', 'HOLLAND ', 'tom@gmail.com', 'pass123', 'DNI', '87654321', '2002-05-15', '948615732', 'M'),
(gen_random_uuid(), '150101', 'ef69fb3d-d4a7-453b-be46-aae44291adf3', 'PEDRO', 'PASCAL', 'pedro@gmail.com', 'pass123', 'CE', '003183661', '2001-03-11', '948761059', 'M'),
(gen_random_uuid(), '130101', 'becf002d-8369-4aaa-8122-715dd4806090', 'SABRINA', 'CARPENTER', 'sabrina@gmail.com', 'pass123', 'PASSPORT', '004876529', '2003-04-07', '946358045', 'F');


SELECT * FROM customers;




--SELECT * FROM auth_authorities;
--SELECT * FROM auth_users;
--SELECT * FROM auth_user_authorities;


