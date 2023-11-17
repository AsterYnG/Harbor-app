UPDATE worker
SET hiring_date = '2020-03-10'
WHERE worker_id =1;

ALTER TABLE freighter_routes
ADD CONSTRAINT che check (count > 0);

INSERT INTO registration(region, city, street, house, flat)
VALUES ('Moscow','Moscow','Vernadskogo',78,1);

INSERT INTO team_member
CREATE

--1 триггер
CREATE or replace function check_salary_amm()
RETURNS trigger as
$$
    BEGIN
        IF NEW.salary > 100000 THEN
            new.salary = 100000;
            new.description = new.description || ' |Salary has been corrected, check it please|';
        end if;
        return new;
    END;
    $$ LANGUAGE plpgsql;

CREATE TRIGGER check_salary
BEFORE INSERT ON position
FOR EACH ROW
EXECUTE procedure check_salary_amm();

--2 триггер
CREATE or replace function check_hiv()
    RETURNS trigger as
$$
BEGIN
    IF (SELECT hiv_status FROM medical_card WHERE med_serial_number = NEW.med_serial_number) = true THEN
        IF NEW.position = 'Cleaner' OR NEW.position = 'Loader' THEN
            RAISE EXCEPTION 'Not permitted job for hiv infected!';
        end if;
    end if;
        return new;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_hiv_status
BEFORE INSERT ON worker
FOR EACH ROW
EXECUTE procedure check_hiv();

INSERT INTO worker(hiring_date, position, med_serial_number, passport_serial_number, education_serial_number, employment_serial_number, dock_id) VALUES
    (now(),'Cleaner','СВ422Б','6016567123','СР556612Д','НР00343',1);

--3 триггер
CREATE or replace function create_order()
    RETURNS trigger as
$$
BEGIN

    IF NEW.order_id > (SELECT max(order_id) max_order FROM "Order") THEN
        INSERT INTO "Order"(date, status) VALUES (now(),'Listed');
        NEW.order_id = (SELECT max(order_id) max_order FROM "Order");
    end if;
    return new;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_order
    BEFORE INSERT ON cargo
    FOR EACH ROW
EXECUTE procedure create_order();



INSERT INTO cargo(cargo_weight, is_fragile, cargo_size, customer_id, freighter_id, order_id, destination) VALUES
                                                                                                             (100,true,100,1,3,5,'Brazil');

--4 триггер
CREATE or replace function create_log()
    RETURNS trigger as
$$
BEGIN
    IF NEW.in_use = true THEN
        INSERT INTO voyage_log(ship_id, shipment_date) VALUES (NEW.ship_id,now());
    end if;
    return NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_log
    AFTER UPDATE ON ship
    FOR EACH ROW
EXECUTE procedure create_log();

UPDATE ship
SET in_use = true
WHERE ship_id = 1;

--5 триггер
CREATE or replace function worker_up()
    RETURNS trigger as
$$
BEGIN
    IF NEW.position = 'Top ' || OLD.position THEN
        IF date_part('year',age(OLD.hiring_date)) < 10 THEN
            RAISE EXCEPTION 'Not enough experience';
end if;
    end if;
    return new;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER up_worker
    BEFORE UPDATE ON worker
    FOR EACH ROW
EXECUTE procedure worker_up();

UPDATE worker
SET position = 'Top Accounter'
WHERE worker_id = 1;


--1 функция
CREATE OR REPLACE FUNCTION profitable_freighter()
RETURNS TABLE (
                freighter varchar,
                sum int
              )
    LANGUAGE plpgsql AS $$
    BEGIN
        RETURN QUERY
        SELECT freighter_name, tax FROM freighter
        ORDER BY tax DESC LIMIT 1;
    end;
    $$;

--2 функция
CREATE OR REPLACE FUNCTION find_ship_mileage(ship_id_temp int)
    RETURNS int

    LANGUAGE plpgsql AS $$
    DECLARE result int;
BEGIN
    select count(*) into result FROM voyage_log
        WHERE voyage_log.ship_id = ship_id_temp;
    RETURN result;
end;
$$;

--3 функция ЛЕВЬ
CREATE OR REPLACE FUNCTION order_count(order_id_temp int)
    RETURNS TABLE (
                    cargo_id int,
                    cargo_weight int,
                    cargo_size int,
                    destination varchar,
                    customer_name varchar,
                    email varchar,
                    freighter_name varchar
                  )
    LANGUAGE plpgsql AS $$
BEGIN
    return query
    select cr.cargo_id, cr.cargo_weight, cr.cargo_size, cr.destination, customer.full_name, customer.email, f.freighter_name FROM cargo cr
    JOIN customer on cr.customer_id = customer.customer_id
    JOIN public.freighter f on f.freighter_id = cr.freighter_id
    WHERE order_id = order_id_temp;
end;
$$;

select * FROM order_count(3);

--3 функция
CREATE OR REPLACE FUNCTION count_cargo_price(cargo_id_temp int)
    RETURNS float
    LANGUAGE plpgsql AS $$
    DECLARE result float;
BEGIN
    SELECT cargo_weight * f.weight_cost + (case when is_fragile then 1 else 0 end) * f.fragile_cost  + cargo_size * f.size_cost into result FROM cargo
        JOIN public.freighter f on f.freighter_id = cargo.freighter_id
        WHERE cargo.cargo_id = cargo_id_temp;
    RETURN result;
end;
$$;

SELECT count_cargo_price(3);

--4 функция
CREATE OR REPLACE FUNCTION show_workers_by_dock_id(dock_id1 int)
    returns TABLE (
        full_name varchar,
        dock_id int,
        position1 varchar,
        salary int,
        hiring_date date
                  )
    LANGUAGE plpgsql AS $$
BEGIN
    return query SELECT p.full_name,d.dock_id,p2.position,p2.salary, worker.hiring_date FROM worker
        JOIN dock d on d.dock_id = worker.dock_id
        JOIN passport p on p.passport_serial_number = worker.passport_serial_number
        JOIN position p2 on p2.position = worker.position
        WHERE d.dock_id = dock_id1;
end;
$$;

SELECT * FROM show_workers_by_dock_id(1);



--5 функция
CREATE OR REPLACE FUNCTION find_most_ship_capacity()
    returns TABLE (
                      ship_id int,
                      ship_capacity int,
                      ship_model varchar,
                      ship_size int,
                      freighter_name varchar
                  )
    LANGUAGE plpgsql AS $$
BEGIN
    return query SELECT s.ship_id, ship_model.ship_capacity, ship_model.ship_model , ship_model.ship_size, f.freighter_name FROM ship_model
        JOIN public.ship s on ship_model.ship_model = s.ship_model
        JOIN public.freighter f on f.freighter_id = s.freighter_id
            ORDER BY ship_capacity DESC
            LIMIT 1;
end;
$$;

SELECT * FROM find_most_ship_capacity();

DELETE FROM position
    WHERE position.position = 'Barman';
INSERT INTO position(position, salary, description) VALUES ('Barman',1000000,'Mix coctailes');


-- Селекция
SELECT * FROM position
WHERE position = 'Accounter';

--Проекция
Select description FROM position;

-- Объединение

SELECT full_name,position FROM team_member
UNION
SELECT full_name,position from worker
JOIN public.passport p on worker.passport_serial_number = p.passport_serial_number;

--Соединение
SELECT * FROM worker
JOIN public.position p on p.position = worker.position;

--Пересечение
select full_name FROM passport
JOIN worker w ON w.passport_serial_number = passport.passport_serial_number
INTERSECT (SELECT full_name FROM team_member);

--Разность
select full_name FROM passport
                          JOIN worker w ON w.passport_serial_number = passport.passport_serial_number
EXCEPT (SELECT full_name FROM team_member);

--Группировка
SELECT cargo_weight FROM cargo
GROUP BY cargo_weight
ORDER BY cargo_weight;


-- Сортировка
select position.position FROM position
ORDER BY salary DESC;


--Деление
SELECT team.team_id FROM team
JOIN team_member tm on team.team_id = tm.team_id
WHERE tm.position IN ('Cook','Worker','Captain')
GROUP BY team.team_id
HAVING count(Distinct position) = 3;


select find_ship_mileage(1);
select profitable_freighter();
select order_count(2);


select * FROM dock
where dock_id=2;

SELECT full_name FROM customer;

select * FROM worker
JOIN public.passport p on p.passport_serial_number = worker.passport_serial_number;


SELECT full_name, position FROM team_member
UNION
SELECT full_name, worker.position FROM worker
JOIN passport on passport.passport_serial_number = worker.passport_serial_number;

SELECT full_name FROM team_member
EXCEPT
SELECT full_name FROM worker
JOIN passport on passport.passport_serial_number = worker.passport_serial_number;

SELECT full_name FROM team_member
INTERSECT
SELECT full_name FROM worker
                          JOIN passport on passport.passport_serial_number = worker.passport_serial_number;

SELECT customer.customer_id, cargo_id FROM cargo
JOIN customer on customer.customer_id = cargo.customer_id
GROUP BY customer.customer_id, cargo_id
ORDER BY cargo_id DESC;

SELECT team.team_id FROM team
JOIN public.team_member tm on team.team_id = tm.team_id
WHERE tm.position IN ('Cook', 'Worker', 'Captain')
GROUP BY team.team_id
HAVING count(distinct position) = 3;


