INSERT INTO customer(full_name, email, password) VALUES
            ('Revunou Arseniy','seny.03@mail.ru','Seny1'),
('Mihail Valenok','valenok@mail.ru','FRYZINO!'),
('Vadimian TuttiFrutti','TutVad@mail.ru','Tx11');

SELECT * FROM customer
Order By customer_id;

INSERT INTO freighter(tax, weight_cost, size_cost, fragile_cost, freighter_name) VALUES
    (10,5,5,100,'Baltimor'),
    (7,8,8,150,'Russian Ocean Transfer'),
    (13,10,10,40,'American Sea');

SELECT * FROM freighter
ORDER BY freighter_id;

INSERT INTO cargo(cargo_weight, is_fragile, cargo_size, customer_id, freighter_id, order_id, destination) VALUES
    (100,true,100,1,3,1,'Brazil'),
    (20,false,1,2,2,2,'Norway'),
    (1,false,10,3,1,3,'United States');


INSERT INTO "Order"(date, status) VALUES
    (now(),'Canceled');

ALTER TABLE "Order"
    ALTER COLUMN date TYPE TIMESTAMP(0);


SELECT * FROM "Order"
    ORDER BY order_id;

select * FROM cargo
    ORDER BY cargo_id;

SELECT full_name,freighter_name,date,status
    FROM "Order"
    JOIN public.cargo c on "Order".order_id = c.order_id
    JOIN public.freighter f on f.freighter_id = c.freighter_id
    JOIN public.customer c2 on c.customer_id = c2.customer_id
    ORDER BY c.order_id;


INSERT INTO medical_card(hiv_status, illness, med_serial_number) VALUES
    (false,'Good','А1134СВ'),
    (false,'Poor eyesight','АВ4551'),
    (false,'Good','СВ5421А');

INSERT INTO education(grade, establishment, education_serial_number) VALUES
    ('Bachelor','RTU MIREA','СВ5671АРП'),
    ('Secondary general','МБОУ Лицей №24','СА42351АПП'),
    ('Master','MGIMO','СВ556678П');


INSERT INTO registration(region, city, street, house, flat) VALUES
    ('Rostov','Volgodonsk','Kurchatova','56',4),
    ('Moscow','Moscow','Lobachevskogo','88',703),
    ('Penza','Penza','Voenniy Gorodok','203',33);

ALTER TABLE passport
ALTER COLUMN birth_date TYPE date;

INSERT INTO passport(full_name, birth_date, sex, citizenship, reg_id, passport_serial_number) VALUES
    ('Aleksandr Chaykin','2003-03-12','Male','Russian',3,'6023144530'),
    ('Aleksey Lazaryushko','2003-05-12','Male','Russian',2,'6024144230'),
    ('Lev Skufenko','2003-03-12','Male','Ukrainian',1,'6023542530');

INSERT INTO employment(previous_job, experience, employment_serial_number) VALUES
    ('None','0','НР12345'),
    ('Cleaner','3','НР124545'),
    ('Engineer Designer','1','НР00045');

INSERT INTO position(position, salary, description) VALUES
    ('Cleaner',500,'Clean docks'),
    ('Loader',1000,'Load cargos'),
    ('Accounter',2000,'Account documents');

ALTER TABLE worker
    ALTER COLUMN hiring_date TYPE date;

INSERT INTO dock(ship_capacity, freighter_id) VALUES
    (3,1),
    (2,2),
    (4,3);

SELECT * FROM dock;


INSERT INTO worker(hiring_date, position, med_serial_number, passport_serial_number, education_serial_number, employment_serial_number, dock_id) VALUES
    ('2023-10-20','Accounter','А1134СВ','6023144530','СВ5671АРП','НР12345',null),
    ('2023-10-20','Accounter','А1134СВ','6023144530','СВ5671АРП','НР12345',1),
    ('2023-10-20','Accounter','А1134СВ','6023144530','СВ5671АРП','НР12345',2);

SELECT *FROM worker;


INSERT INTO store_house(capacity, dock_id) VALUES
    (10000,1),
    (1000,1),
    (50000,2),
    (100000,3);

INSERT INTO team(expirience, description) VALUES
    (10,'Accurate team'),
    (3,'Average team'),
    (1,'Low skill team');


ALTER TABLE team_member
ALTER COLUMN birth_date TYPE DATE;

INSERT INTO team_member(full_name, position, birth_date, citizenship, team_id) VALUES
    ('Vladimir Kimberbetch','Captain','1999-4-10','Russian',1),
    ('Mihail Tolmachev','Worker','1989-3-07','Russian',1),
    ('Oleg Menchko','Captain','1998-5-11','Russian',2),
    ('Dmitriy Holo','Worker','2000-10-10','Russian',2),
    ('Oleg Kamichev','Captain','1978-8-06','Russian',3),
    ('Vladimir Pufin','Worker','1999-4-12','Russian',3);


INSERT INTO ship_model(ship_model, ship_capacity, ship_size) VALUES
    ('A1',1000,100),
    ('B2',10000,1000),
    ('C1',100000,10000);


INSERT INTO ship(in_use, team_id, ship_model, freighter_id) VALUES
    (false,1,'A1',1),
    (false,2,'B2',2),
    (false,3,'C1',3);

ALTER TABLE available_routes
DROP COLUMN duration;

ALTER TABLE available_routes
ADD COLUMN duration INTEGER NOT NULL ;

INSERT INTO available_routes(destination_country, destination_city, duration) VALUES
    ('Norway','Oslo',72),
    ('United States','Detroit',118),
    ('Brazil','Brazilia',700);

SELECT * FROM available_routes;

SELECT * FROM freighter_routes;

INSERT INTO freighter_routes(freighter_id, route_id, count) VALUES
    (1,1,1),
    (1,2,1),
    (2,1,1),
    (2,3,1),
    (3,1,1),
    (3,2,1),
    (3,3,1);

INSERT INTO voyage_log(ship_id, shipment_date) VALUES
    (1,'2023-10-29 18:01:00'),
    (2,'2023-9-29 12:01:00'),
    (3,'2023-10-26 13:34:00');

