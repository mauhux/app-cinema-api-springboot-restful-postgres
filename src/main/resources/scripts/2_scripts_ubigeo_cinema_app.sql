-- ============================================================================
-- CINEMA MANAGEMENT SYSTEM – UBIGEO SCHEMA (PostgreSQL)
-- ============================================================================
-- Description:
-- This script creates and populates the geographical tables for Peru (departments,
-- provinces, and districts) based on the official UBIGEO format.
-- ============================================================================


-- ============================================================================
-- 1. TABLE CREATION: Departments, Provinces, Districts
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
-- 2. DATA CLEANUP (USEFUL DURING DEVELOPMENT RESET)
-- ============================================================================

-- Deletes records from authentication tables if previously inserted
DELETE FROM auth_user_authorities;
DELETE FROM auth_users;
DELETE FROM auth_authorities;


-- ============================================================================
-- 3. DATA INSERTION – UBIGEO DATASET (Partial)
-- ============================================================================

-- -----------------------------------------
-- 3.1 Insert Departments (12 total)
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
-- 3.2 Insert Provinces (1+ per department)
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


-- -----------------------------------------
-- 3.3 Insert Districts (linked to provinces)
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
-- 4. FINAL QUERY – COMPLETE UBIGEO VIEW
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

-- Search for all districts in a specific department
SELECT
    d.district_id,
    d.district_name,
    prov.province_name,
    dep.department_name
FROM districts d
JOIN provinces prov ON d.province_id = prov.province_id
JOIN departments dep ON prov.department_id = dep.department_id
WHERE dep.department_name = 'LIMA'
ORDER BY prov.province_name, d.district_name;

-- Search provinces by department
SELECT
    prov.province_id,
    prov.province_name
FROM provinces prov
JOIN departments dep ON prov.department_id = dep.department_id
WHERE dep.department_name = 'PUNO';

-- Count how many provinces each department has
SELECT
    dep.department_name,
    COUNT(prov.province_id) AS total_provinces
FROM departments dep
JOIN provinces prov ON prov.department_id = dep.department_id
GROUP BY dep.department_name
ORDER BY total_provinces DESC;

-- Count how many districts each province has
SELECT
    prov.province_name,
    dep.department_name,
    COUNT(d.district_id) AS total_districts
FROM provinces prov
JOIN departments dep ON prov.department_id = dep.department_id
JOIN districts d ON d.province_id = prov.province_id
GROUP BY prov.province_name, dep.department_name
ORDER BY total_districts DESC;

-- Search district by UBIGEO code (input from a system or API)
SELECT
    d.district_id,
    d.district_name,
    prov.province_name,
    dep.department_name
FROM districts d
JOIN provinces prov ON d.province_id = prov.province_id
JOIN departments dep ON prov.department_id = dep.department_id
WHERE d.district_id = '150101';

-- Autocomplete provinces by department ID (useful for dependent combo on frontend)
SELECT
    province_id,
    province_name
FROM provinces
WHERE department_id = '21' -- PUNO
ORDER BY province_name;

-- Autocomplete districts by province ID
SELECT
    district_id,
    district_name
FROM districts
WHERE province_id = '2111' -- SAN ROMÁN
ORDER BY district_name;



