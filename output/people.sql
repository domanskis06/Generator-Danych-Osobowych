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

INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('STANISŁAW', 'SKUBALA', '1987-03-23', 'MALE', '87032339773', 'Katowice', 'Czesława Miłosza', 'stanisław.skubala@gmail.com', '+48628726785');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('OLIVIER', 'BILKO', '1959-12-29', 'MALE', '59122974610', 'Hajnówka', 'Wspólna', 'olivier.bilko@gmail.com', '+48129507600');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('JEREMI', 'TROCIŃSKI', '1956-04-22', 'MALE', '56042225097', 'Gdańsk', 'Sosnowa', 'jeremi.trociński@gmail.com', '+48811184492');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('AMELIA', 'PASZKOWSKA', '2001-09-24', 'FEMALE', '01292448022', 'Mysłowice', 'Sosnowa', 'amelia.paszkowska@gmail.com', '+48823566666');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('OKSANA', 'SZYMAŃCZYK', '2004-12-16', 'FEMALE', '04321647306', 'Bytom', 'Konstantego Jelskiego', 'oksana.szymańczyk@gmail.com', '+48870714086');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('JOANNA', 'WOJEWODA', '1964-01-19', 'FEMALE', '64011956525', 'Żory', 'Lawendowa', 'joanna.wojewoda@gmail.com', '+48217761835');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('IDA', 'KIDOŃ', '1979-06-05', 'FEMALE', '79060557283', 'Poznań', 'Bukowa', 'ida.kidoń@gmail.com', '+48315333861');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('TADEUSZ', 'MIELCZAREK', '1992-06-16', 'MALE', '92061613633', 'Warszawa', 'Komuny Paryskiej', 'tadeusz.mielczarek@gmail.com', '+48567073764');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('DARYA', 'MAZURKIEWICZ', '1979-09-24', 'FEMALE', '79092441943', 'Bytom', 'Pogodna', 'darya.mazurkiewicz@gmail.com', '+48237901111');
INSERT INTO people (first_name, last_name, birth_date, gender, pesel, city, street, email, phone) VALUES ('BARTOSZ', 'JASIKOWSKI', '1976-04-03', 'MALE', '76040377015', 'Kostrzyn', 'Kasztanowa', 'bartosz.jasikowski@gmail.com', '+48663557490');
