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

INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MAJA', 'PAJĄK', '1952-06-01', 'FEMALE', '52060138924', 'Lwówek', 'Pogodna', 'maj_1952@onet.pl', '+48439816923');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MARTA', 'BARYGA', '1950-02-15', 'FEMALE', '50021584948', 'Bydgoszcz', 'Basztowa', 'martabaryga@o2.pl', '+48624334223');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('BOGUSŁAWA', 'REĆKO', '1995-05-29', 'FEMALE', '95052977543', 'Szczecin', 'Krępiecka', 'reck.boguslawa.1995@gmail.com', '+48693221832');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('ALEKSY', 'RYLSKI', '1981-05-24', 'MALE', '81052482938', 'Kraków', 'Sybiraków', 'aleksy_ryl841@outlook.com', '+48520448120');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('JAN', 'BELEĆ', '1994-01-03', 'MALE', '94010377054', 'Radomsko', 'Północna', 'jan1994belec@yahoo.com', '+48412438512');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('KAZIMIERZ', 'PAVLUSHCHYK', '1992-04-24', 'MALE', '92042424951', 'Siedlce', 'Laskowa', 'kaz_pavlushchyk@yahoo.com', '+48241179308');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('MARIA', 'PRYMACHUK', '2001-11-12', 'FEMALE', '01311263087', 'Konin', 'Staszica', 'mar.01.prymachuk@outlook.com', '+48118864017');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('TERESA', 'REZNIK', '1962-02-04', 'FEMALE', '62020487863', 'Ozorków', 'Powstania Styczniowego', 'tere690@outlook.com', '+48200171924');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('KACPER', 'GIERCZAK', '1952-05-27', 'MALE', '52052731612', 'Krajenka', 'Sarmatów', 'kacper@outlook.com', '+48131093134');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('KAROLINA', 'KORZONEK', '1976-03-10', 'FEMALE', '76031085020', 'Żywiec', 'Plac Zwycięstwa', 'karol.1976@outlook.com', '+48436269479');
