INSERT INTO addresses (country, city, state, street, postal_code) VALUES
    ('USA', 'New York', 'NY', '5th Avenue', '10001'),
    ('Germany', 'Berlin', 'Berlin', 'Unter den Linden', '10117'),
    ('France', 'Paris', 'Ile-de-France', 'Champs-Élysées', '75008'),
    ('Russia', 'Moscow', 'Moscow', 'Tverskaya Street', '125009'),
    ('UK', 'London', 'Greater London', 'Baker Street', 'NW1 6XE');

INSERT INTO autosalons (address_id, name) VALUES
    (1, 'AutoStar NYC'),
    (2, 'Berlin AutoHaus'),
    (3, 'Paris Prestige Cars'),
    (4, 'Moscow Motors'),
    (5, 'London Luxury Rides');

INSERT INTO users (username, password, role) VALUES
    ('john_doe1', '$2a$10$jw8TI/OVKyrHI1Y0ajlDeOE7676ONeCjbLxibmx3Fq.K5s/K/6w7S', 'ADMIN'),
    ('jane_smith', '$2a$10$jw8TI/OVKyrHI1Y0ajlDeOE7676ONeCjbLxibmx3Fq.K5s/K/6w7S', 'EMPLOYEE'),
    ('mike_jones', '$2a$10$jw8TI/OVKyrHI1Y0ajlDeOE7676ONeCjbLxibmx3Fq.K5s/K/6w7S', 'CLIENT'),
    ('anna_brown', '$2a$10$jw8TI/OVKyrHI1Y0ajlDeOE7676ONeCjbLxibmx3Fq.K5s/K/6w7S', 'EMPLOYEE'),
    ('david_clark', '$2a$10$jw8TI/OVKyrHI1Y0ajlDeOE7676ONeCjbLxibmx3Fq.K5s/K/6w7S', 'CLIENT');

INSERT INTO persons (first_name, last_name) VALUES
    ('John', 'Doe'),
    ('Jane', 'Smith'),
    ('Michael', 'Johnson'),
    ('Anna', 'Brown'),
    ('David', 'Clark');

INSERT INTO employees (person_id, user_id, autosalon_id, address_id, position, salary) VALUES
    (2, 2, 1, 1, 'Sales Manager', 50000),
    (4, 4, 2, 2, 'Technician', 45000),
    (3, 2, 3, 3, 'Salesperson', 40000),
    (5, 4, 4, 4, 'Sales Consultant', 48000),
    (1, 2, 5, 5, 'Manager', 55000);

INSERT INTO clients (person_id, user_id, address_id, birth_date, passport_number) VALUES
    (1, 1, 1, '1985-05-20', 'AB123456'),
    (3, 3, 2, '1990-07-15', 'CD789012'),
    (5, 5, 3, '1992-03-25', 'EF345678'),
    (2, 1, 4, '1988-12-10', 'GH901234'),
(4, 3, 5, '1987-11-05', 'IJ567890');

INSERT INTO cars (make, model, vin, year, color, body_type, engine_type, fuel_type, transmission, status) VALUES
    ('BMW', 'X5', 'WBADE6329VB567890', 2023, 'Black', 'SUV', 'V6', 'Gasoline', 'Automatic', 'Available'),
    ('Audi', 'A4', 'WAUEFAFLXEA123456', 2022, 'White', 'Sedan', 'I4', 'Diesel', 'Manual', 'Sold'),
    ('Mercedes-Benz', 'C-Class', 'WDDGF8AB8CA678901', 2021, 'Silver', 'Sedan', 'V6', 'Hybrid', 'Automatic', 'Available'),
    ('Tesla', 'Model S', '5YJSA1E44FF098765', 2024, 'Red', 'Sedan', 'Electric', 'Electric', 'Automatic', 'Available'),
    ('Toyota', 'RAV4', 'JTMRFREV9HJ123789', 2020, 'Blue', 'SUV', 'I4', 'Gasoline', 'CVT', 'Sold');

INSERT INTO autosalons_cars (autosalon_id, car_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO reviews (client_id, description, rating) VALUES
    (1, 'Great service!', 4.5),
    (2, 'Friendly staff.', 4.0),
    (3, 'Not the best experience.', 3.5),
    (4, 'Amazing dealership!', 5.0),
    (5, 'Could improve the customer service.', 3.0),
    (1, 'Excellent service and professional staff!', 5),
    (2, 'Good dealership, but the waiting time was long.', 4),
    (3, 'Average experience, lacked attention to detail.', 3),
    (4, 'Poor service quality, disappointed.', 2),
    (5, 'Terrible experience, will not come back.', 1);

INSERT INTO dealers (address_id, name) VALUES
    (1, 'Global Motors'),
    (2, 'European Auto Export'),
    (3, 'Paris Auto Partners'),
    (4, 'Moscow Vehicle Traders'),
    (5, 'London Car Exchange');

INSERT INTO dealers_reviews (dealer_id, review_id) VALUES
    (4, 4),
    (5, 5);

INSERT INTO autosalons_reviews (autosalon_id, review_id) VALUES
    (1, 6),
    (2, 7),
    (3, 8),
    (4, 9),
    (5, 10);

INSERT INTO sales (autosalon_id, client_id, car_id, employee_id, sale_date, discount, final_price, payment_method, configuration, warranty_period) VALUES
    (1, 1, 1, 1, '2023-06-15', 0.05, 57000, 'Credit Card', 'Full Option', 24),
    (2, 2, 2, 2, '2023-08-20', 0.10, 36000, 'Cash', 'Standard', 12),
    (3, 3, 3, 3, '2023-09-05', 0.15, 46750, 'Wire Transfer', 'Premium', 36),
    (4, 4, 4, 4, '2023-10-12', 0.08, 69000, 'Debit Card', 'Performance', 48),
    (5, 5, 5, 5, '2023-11-01', 0.12, 30800, 'Credit Card', 'Limited', 18);

INSERT INTO contacts (contact_type, contact_value) VALUES
    ('PHONE', '+1-212-555-1234'),
    ('EMAIL', 'info@berlinautohaus.com'),
    ('SOCIAL_MEDIA', '@parisprestigecars'),
    ('FAX', '+7-495-555-6789'),
    ('PHONE', '+44-20-7946-0958');

INSERT INTO test_drives (autosalon_id, car_id, client_id, test_drive_date, status) VALUES
    (1, 1, 1, '2023-05-20', 'Completed'),
    (2, 2, 2, '2023-06-25', 'Scheduled'),
    (3, 3, 3, '2023-07-10', 'Completed'),
    (4, 4, 4, '2023-08-15', 'Canceled'),
    (5, 5, 5, '2023-09-01', 'Completed');

INSERT INTO dealers_cars (dealer_id, car_id, price) VALUES
    (1, 1, 60000),
    (2, 2, 40000),
    (3, 3, 55000),
    (4, 4, 75000),
    (5, 5, 35000);

INSERT INTO dealers_contacts (dealer_id, contact_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO autosalons_contacts (autosalon_id, contact_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO persons_contacts (person_id, contact_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO cars_reviews (car_id, review_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3)
