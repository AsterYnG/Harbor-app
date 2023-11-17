--Селекция
SELECT * FROM dock
WHERE dock_id = 1;

--Проекция
Select email FROM customer;

--Объединение
SELECT full_name,position FROM team_member
UNION
SELECT full_name,position from worker
                                   JOIN public.passport p on worker.passport_serial_number = p.passport_serial_number;

--Соединение
SELECT * FROM worker
                  JOIN passport p on p.passport_serial_number = worker.passport_serial_number;

--Пересечение
select full_name FROM passport
                          JOIN worker w ON w.passport_serial_number = passport.passport_serial_number
INTERSECT (SELECT full_name FROM team_member);

--Разность
select full_name FROM passport
                          JOIN worker w ON w.passport_serial_number = passport.passport_serial_number
EXCEPT (SELECT full_name FROM team_member);

--Группировка
SELECT capacity FROM store_house
GROUP BY capacity;

--Сортировка
select * FROM team
ORDER BY expirience DESC;

--Деление
SELECT team.team_id FROM team
                             JOIN team_member tm on team.team_id = tm.team_id
WHERE tm.position IN ('Cook','Worker','Captain')
GROUP BY team.team_id
HAVING count(Distinct position) = 3;


ALTER TABLE customer
ADD COLUMN phone_number text;

ALTER TABLE customer
DROP COLUMN phone_number;

UPDATE customer
SET full_name = 'O O'
WHERE customer_id = 1;

create or replace function create_ship_model() returns trigger
    language plpgsql
as
$$
BEGIN
    if not exists (select sm.ship_model from ship_model as sm where sm.ship_model = new.ship_model) then
        INSERT INTO ship_model (ship_model, ship_capacity, ship_size) VALUES (
                                                                              new.ship_model,
                                                                              10000,
                                                                              1000
                                                                             );
    end if;
    return new;
END;
$$;

create or replace trigger create_ship_model
BEFORE INSERT on ship
FOR EACH ROW
EXECUTE PROCEDURE create_ship_model();

INSERT INTO ship(in_use, team_id, ship_model, freighter_id) VALUES (
                                                                    false,
                                                                    1,
                                                                    'SS789',
                                                                    1
                                                                   );
