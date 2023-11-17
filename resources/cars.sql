CREATE TABLE cars (
    brend VARCHAR(150) NOT NULL ,
    cost INTEGER NOT NULL ,
    owner_id INTEGER NOT NULL,
    car_id SERIAL PRIMARY KEY
);

CREATE TABLE owner (
    full_name VARCHAR(150),
    owner_id SERIAL PRIMARY KEY,
    age INTEGER NOT NULL ,
    sex VARCHAR(150) NOT NULL
);

ALTER TABLE cars
ADD FOREIGN KEY (owner_id) REFERENCES owner(owner_id);

INSERT INTO owner(full_name, age, sex)
VALUES ('Александр Чайкин',15,'М'),
       ('Миша Фрязик',19,'М'),
       ('Алина Зарубина',20,'Ж'),
       ('Алексей Лазарев',19,'М'),
       ('Мистер Бонд',30,'М'),
       ('Инесса Молодняк',18,'Ж');

INSERT INTO cars (brend, cost, owner_id)
VALUES ('BMW',160,null);

SELECT car_id,brend,full_name from cars
JOIN owner o on cars.owner_id = o.owner_id;


SELECT * FROM owner;

UPDATE owner
SET sex = 'Было' WHERE full_name = 'Алина Зарубина';

ALTER TABLE cars
ADD COLUMN image bytea;


ALTER TABLE cars
ADD CONSTRAINT uni UNIQUE (cost);

ALTER TABLE cars
ADD CONSTRAINT che CHECK ( cost < 1000000 );

INSERT INTO cars(brend, cost, owner_id) VALUES ('Fdsf',10000000,1);

SELECT brend, full_name
FROM cars,owner;

SELECT * FROM cars
LEFT JOIN owner o on cars.owner_id = o.owner_id
WHERE o.owner_id IS NULL;

CREATE table channels (slug TEXT, code INTEGER);

INSERT INTO channels (slug, code) VALUES ( '2x2', 10 );
INSERT INTO channels (slug, code) VALUES ( 'CTC', 20 );
INSERT INTO channels (slug, code) VALUES ( 'PornoTv', 30 );

CREATE table prohibited_channels (slug TEXT, code INTEGER);

INSERT INTO prohibited_channels (slug, code) VALUES ( '2x2', 11 );
INSERT INTO prohibited_channels (slug, code) VALUES ( 'PornoTv', 31 );

SELECT * FROM channels NATURAL LEFT JOIN prohibited_channels WHERE prohibited_channels IS NOT NULL;

SELECT * FROM cars
WHERE EXISTS(SELECT * FROM cars WHERE brend = 'BMW');

SELECT * FROM cars WHERE brend = 'BMW';


CREATE OR REPLACE FUNCTION show_updated_string()
RETURNS trigger AS
$$
    BEGIN
        INSERT INTO cars(brend, cost, owner_id) VALUES ('Запорожец',15,5);
    RETURN NEW;
end;
$$
LANGUAGE plpgsql;

CREATE TRIGGER hello
AFTER INSERT ON owner FOR EACH STATEMENT
EXECUTE procedure show_updated_string();

DROP TRIGGER hello ON owner;

INSERT INTO cars(brend, cost, owner_id) VALUES ('Nissan',1234,null);

SELECT FROM cars
    UNION
SELECT * FROM owner;


DELETE FROM owner
WHERE full_name = 'asd';

INSERT INTO owner(full_name, age, sex) VALUES ('asd',5,'vee');