CREATE TYPE role_enum AS ENUM ('CLIENT', 'EMPLOYEE', 'ADMIN');
CREATE TYPE contact_type_enum AS ENUM ('PHONE', 'EMAIL', 'SOCIAL_MEDIA', 'FAX');

CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50),
    street VARCHAR(50) NOT NULL,
    postal_code VARCHAR(10) NOT NULL
);

CREATE TABLE autosalons (
    id SERIAL PRIMARY KEY,
    address_id INT REFERENCES addresses(id) ON DELETE SET NULL,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL CHECK (LENGTH(username) > 8),
    password VARCHAR(255) NOT NULL CHECK (LENGTH(password) > 8),
    role role_enum NOT NULL,
    created_at DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE persons (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    person_id INT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    autosalon_id INT REFERENCES autosalons(id) ON DELETE SET NULL,
    address_id INT REFERENCES addresses(id) ON DELETE SET NULL,
    position VARCHAR(50) NOT NULL,
    salary NUMERIC NOT NULL CHECK (salary > 0)
);

CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    person_id INT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE SET NULL,
    address_id INT NOT NULL REFERENCES addresses(id),
    birth_date DATE NOT NULL CHECK (birth_date <= CURRENT_DATE),
    passport_number VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    vin VARCHAR(17) NOT NULL UNIQUE,
    year INT NOT NULL CHECK (year > 1885 AND year <= EXTRACT(YEAR FROM CURRENT_DATE)),
    color VARCHAR(30) NOT NULL,
    body_type VARCHAR(30) NOT NULL,
    engine_type VARCHAR(30) NOT NULL,
    fuel_type VARCHAR(30) NOT NULL,
    transmission VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE dealers (
    id SERIAL PRIMARY KEY,
    address_id INT REFERENCES addresses(id) ON DELETE SET NULL,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE autosalons_cars (
    autosalon_id INT NOT NULL REFERENCES autosalons(id) ON DELETE CASCADE,
    car_id INT NOT NULL REFERENCES cars(id) ON DELETE CASCADE,
    PRIMARY KEY (autosalon_id, car_id)
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    description TEXT NOT NULL,
    rating NUMERIC NOT NULL CHECK (rating >= 0 AND rating <= 5),
    created_at DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE dealers_reviews (
    dealer_id INT NOT NULL REFERENCES dealers(id) ON DELETE CASCADE,
    review_id INT NOT NULL REFERENCES reviews(id) ON DELETE CASCADE,
    PRIMARY KEY (dealer_id, review_id)
);

CREATE TABLE autosalons_reviews (
    autosalon_id INT NOT NULL REFERENCES autosalons(id) ON DELETE CASCADE,
    review_id INT NOT NULL REFERENCES reviews(id) ON DELETE CASCADE,
    PRIMARY KEY (autosalon_id, review_id)
);

CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    autosalon_id INT NOT NULL REFERENCES autosalons(id) ON DELETE CASCADE,
    client_id INT NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    car_id INT NOT NULL REFERENCES cars(id) ON DELETE CASCADE,
    employee_id INT REFERENCES employees(id) ON DELETE SET NULL,
    sale_date DATE NOT NULL,
    discount NUMERIC DEFAULT 0 CHECK (discount >= 0 AND discount <= 1),
    final_price NUMERIC NOT NULL CHECK (final_price > 0),
    payment_method VARCHAR(50) NOT NULL,
    configuration VARCHAR(20),
    warranty_period INT NOT NULL CHECK (warranty_period >= 0)
);

CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    contact_type contact_type_enum NOT NULL,
    contact_value VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE test_drives (
    id SERIAL PRIMARY KEY,
    autosalon_id INT NOT NULL REFERENCES autosalons(id) ON DELETE CASCADE,
    car_id INT NOT NULL REFERENCES cars(id) ON DELETE CASCADE,
    client_id INT NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    test_drive_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE dealers_cars (
      id SERIAL PRIMARY KEY,
      dealer_id INT NOT NULL REFERENCES dealers(id),
      car_id INT NOT NULL REFERENCES cars(id),
      price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE dealers_contacts (
    dealer_id INT NOT NULL REFERENCES dealers(id) ON DELETE CASCADE,
    contact_id INT NOT NULL REFERENCES contacts(id) ON DELETE CASCADE,
    PRIMARY KEY (dealer_id, contact_id)
);

CREATE TABLE autosalons_contacts (
    autosalon_id INT NOT NULL REFERENCES autosalons(id) ON DELETE CASCADE,
    contact_id INT NOT NULL REFERENCES contacts(id) ON DELETE CASCADE,
    PRIMARY KEY (autosalon_id, contact_id)
);

CREATE TABLE persons_contacts (
    person_id INT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    contact_id INT NOT NULL REFERENCES contacts(id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, contact_id)
);

CREATE TABLE cars_reviews (
    car_id INT NOT NULL REFERENCES cars(id) ON DELETE CASCADE,
    review_id INT NOT NULL REFERENCES reviews(id) ON DELETE CASCADE,
    PRIMARY KEY (car_id, review_id)
);
