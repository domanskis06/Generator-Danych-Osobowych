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

INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('PAWEŁ', 'KASZUBA', '1958-08-19', 'MALE', '58081987095', 'Inowrocław', 'Nad Młynówką', 'paweł.kaszuba@gmail.com', '+48588822488');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('ANNA', 'MILEWSKA', '1965-08-06', 'FEMALE', '65080619407', 'Płock', 'Słoneczna', 'anna.milewska@gmail.com', '+48181030413');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('JOANNA', 'WASIELEWSKA', '2000-07-03', 'FEMALE', '00270355462', 'Przemyśl', 'Sudecka', 'joanna.wasielewska@gmail.com', '+48947198964');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('WIESŁAWA', 'NAPIERAŁA', '1951-02-06', 'FEMALE', '51020626367', 'Szczecin', 'Lasek Miejski', 'wiesława.napierała@gmail.com', '+48361442572');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('SYLWIA', 'MILEWSKA', '1991-07-02', 'FEMALE', '91070287284', 'Nowe Miasto Lubawskie', 'Plac św. Stanisława', 'sylwia.milewska@gmail.com', '+48351274974');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('PIOTR', 'KRZYWY', '1989-06-09', 'MALE', '89060975017', 'Poznań', 'Borki', 'piotr.krzywy@gmail.com', '+48670356795');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('SERGII', 'TAMKUN', '1960-09-18', 'MALE', '60091830533', 'Wieruszów', 'Strumykowa', 'sergii.tamkun@gmail.com', '+48952055475');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('DAWID', 'ŚLESIŃSKI', '1962-12-15', 'MALE', '62121561150', 'Gliwice', 'Polna', 'dawid.ślesiński@gmail.com', '+48668147635');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MARIUSZ', 'KWIATKOWSKI', '1992-08-17', 'MALE', '92081736013', 'Bielsko-Biała', 'Kardynała Stefana Wyszyńskiego', 'mariusz.kwiatkowski@gmail.com', '+48266666481');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('REGINA', 'SZWAJKOWSKA', '1995-07-02', 'FEMALE', '95070294428', 'Nowy Sącz', 'Storczykowa', 'regina.szwajkowska@gmail.com', '+48583981702');
