CREATE TABLE IF NOT EXISTS people (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    birth_date DATE,
    gender VARCHAR(10),
    pesel VARCHAR(11),
    city VARCHAR(100),
    street VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MATEUSZ', 'PIKTEL', '1967-04-29', 'MALE', '67042976012', 'Szczecin', 'Wojsławska', 'mateusz.piktel@gmail.com', '+48577368945');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('ALOJZY', 'KOBERIDZE', '2004-06-06', 'MALE', '04260667472', 'Leszno', 'Ogrodowa', 'alojzy.koberidze@gmail.com', '+48211609459');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('ANNA', 'JASZCZOŁT', '1986-05-26', 'FEMALE', '86052620746', 'Białystok', 'Kowalska', 'anna.jaszczołt@gmail.com', '+48560129390');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('PRZEMYSŁAW', 'ZAJCHER', '2003-08-01', 'MALE', '03280119257', 'Nałęczów', 'Ofiar Oświęcimskich', 'przemysław.zajcher@gmail.com', '+48950868846');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('KAMILA', 'SOKOŁOWSKA', '1960-09-15', 'FEMALE', '60091555182', 'Lidzbark Warmiński', 'Usługowa', 'kamila.sokołowska@gmail.com', '+48744690796');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('OLIWIA', 'SOWA', '1973-08-28', 'FEMALE', '73082850049', 'Jelenia Góra', 'Mikołaja Kopernika', 'oliwia.sowa@gmail.com', '+48755616649');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('PATRYCJA', 'GOSTKIEWICZ', '1969-12-09', 'FEMALE', '69120922546', 'Lubsko', 'Gościeradzka', 'patrycja.gostkiewicz@gmail.com', '+48161244839');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('DOROTA', 'GÓRZYŃSKA', '1959-11-13', 'FEMALE', '59111335341', 'Tarnów', 'Działkowa', 'dorota.górzyńska@gmail.com', '+48282942822');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('AMELIA', 'GORA', '1981-12-10', 'FEMALE', '81121080823', 'Olsztyn', 'Polna', 'amelia.gora@gmail.com', '+48338075140');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('OSKAR', 'KOWALEWSKI', '1989-02-08', 'MALE', '89020852338', 'Sokółka', 'Olgi Boznańskiej', 'oskar.kowalewski@gmail.com', '+48418380751');
