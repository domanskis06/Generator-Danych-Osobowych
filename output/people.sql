CREATE TABLE IF NOT EXISTS people (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    birth_date DATE,
    gender VARCHAR(10),
    pesel VARCHAR(11),
    nip VARCHAR(10),
    id_card_number VARCHAR(20),
    city VARCHAR(100),
    street VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('EWELINA', 'SZARSZEWSKA', '1973-12-01', 'FEMALE', '73120185227', '6274251373', 'UQO769976', 'Świebodzice', 'Zaułek Ossolińskich');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MARIUSZ', 'CZAJA', '2005-04-08', 'MALE', '05240835753', '9201900123', 'XKM335880', 'Świętochłowice', 'Połaniecka');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('ANNA', 'BUDZIŁEK', '1957-08-31', 'FEMALE', '57083128505', '6408822263', 'RKF170017', 'Nowe Warpno', 'gen. Dezyderego Chłapowskiego');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('STANISŁAW', 'SIKORSKI', '1971-04-02', 'MALE', '71040241292', '9866599427', 'WXG889013', 'Katowice', 'stacja metra Stadion Narodowy');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('JOANNA', 'RÓŻEWICZ', '1950-10-05', 'FEMALE', '50100541983', '4949488024', 'EFM944134', 'Barczewo', 'Ludwika Waryńskiego');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('JÓZEFA', 'DUDEK', '1960-03-21', 'FEMALE', '60032162088', '4891963830', 'YAW878991', 'Brwinów', 'Księdza Dzierżona');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('REGINA', 'GŁĘBOCKA', '1952-06-26', 'FEMALE', '52062658329', '7889778693', 'YEI299178', 'Lublin', 'Gen. Kazimierza Sosnkowskiego');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('LECH', 'SAWICKI', '1957-06-27', 'MALE', '57062797131', '3745287387', 'WAC500922', 'Knurów', '1 Maja');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MATEUSZ', 'ZIEWIEC', '1965-04-09', 'MALE', '65040998674', '1368483673', 'FKU131347', 'Wrocław', 'Mirtowa');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('OLIVER', 'SPYRA', '1999-03-22', 'MALE', '99032249758', '1056010781', 'WTS105238', 'Koszalin', 'Grenadierów');
