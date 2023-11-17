CREATE TABLE Available_routes
(
    route_id  SERIAL NOT NULL,
    destination_country  VARCHAR(150) NOT NULL,
    destination_city  VARCHAR(150) NOT NULL,
    duration  TIME NOT NULL
)
;



ALTER TABLE Available_routes
    ADD  PRIMARY KEY (route_id)
;



CREATE TABLE Cargo
(
    cargo_id  SERIAL NOT NULL,
    cargo_weight  INTEGER NOT NULL,
    is_fragile  boolean NOT NULL,
    cargo_size  INTEGER NOT NULL,
    customer_id  INTEGER NOT NULL,
    freighter_id  INTEGER NOT NULL,
    order_id  INTEGER NOT NULL,
    destination  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Cargo
    ADD  PRIMARY KEY (cargo_id)
;



CREATE TABLE Customer
(
    full_name  VARCHAR(150) NOT NULL,
    email  VARCHAR(150) NOT NULL,
    customer_id  SERIAL NOT NULL,
    password  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Customer
    ADD  PRIMARY KEY (customer_id)
;



CREATE TABLE Dock
(
    dock_id  SERIAL NOT NULL,
    ship_capacity  INTEGER NOT NULL,
    freighter_id  INTEGER NULL
)
;



ALTER TABLE Dock
    ADD  PRIMARY KEY (dock_id)
;



CREATE TABLE Education
(
    grade  VARCHAR(150) NOT NULL,
    establishment  VARCHAR(150) NOT NULL,
    education_serial_number  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Education
    ADD  PRIMARY KEY (education_serial_number)
;



CREATE TABLE Employment
(
    previous_job  VARCHAR(150) NULL,
    experience  INTEGER NOT NULL,
    employment_serial_number  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Employment
    ADD  PRIMARY KEY (employment_serial_number)
;



CREATE TABLE Freighter
(
    freighter_id  SERIAL,
    tax  INTEGER NOT NULL,
    weight_cost  INTEGER NOT NULL,
    size_cost  INTEGER NOT NULL,
    fragile_cost  boolean NOT NULL,
    freighter_name VARCHAR(150) NOT NULL

)
;



ALTER TABLE Freighter
    ADD  PRIMARY KEY (freighter_id)
;



CREATE TABLE Freighter_Routes
(
    freighter_id  INTEGER NOT NULL,
    route_id  INTEGER NOT NULL,
    count  INTEGER NULL
)
;



ALTER TABLE Freighter_Routes
    ADD  PRIMARY KEY (freighter_id,route_id)
;



CREATE TABLE Medical_card
(
    hiv_status  boolean NOT NULL,
    illness  VARCHAR(150) NULL,
    med_serial_number  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Medical_card
    ADD  PRIMARY KEY (med_serial_number)
;



CREATE TABLE "Order"
(
    date  TIMESTAMP(0) NOT NULL,
    order_id  SERIAL NOT NULL,
    status  VARCHAR(150) NOT NULL
)
;



ALTER TABLE "Order"
    ADD  PRIMARY KEY (order_id)
;



CREATE TABLE Passport
(
    full_name  VARCHAR(150) NOT NULL,
    birth_date DATE NOT NULL,
    sex  VARCHAR(50) NOT NULL,
    citizenship  VARCHAR(150) NOT NULL,
    reg_id  INTEGER NOT NULL,
    passport_serial_number  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Passport
    ADD  PRIMARY KEY (passport_serial_number)
;



CREATE TABLE Position
(
    position  VARCHAR(150) NOT NULL,
    salary  INTEGER NOT NULL,
    description  VARCHAR(150) NOT NULL
)
;



ALTER TABLE Position
    ADD  PRIMARY KEY (position)
;



CREATE TABLE Registration
(
    reg_id  SERIAL NOT NULL,
    region  VARCHAR(150) NULL,
    city  VARCHAR(150) NOT NULL,
    street  VARCHAR(150) NOT NULL,
    house  VARCHAR(150) NOT NULL,
    flat  INTEGER NULL
)
;



ALTER TABLE Registration
    ADD  PRIMARY KEY (reg_id)
;



CREATE TABLE Ship
(
    ship_id  SERIAL NOT NULL,
    in_use  boolean NOT NULL,
    team_id  INTEGER NOT NULL,
    ship_model  VARCHAR(150) NOT NULL,
    freighter_id  INTEGER NOT NULL
)
;



ALTER TABLE Ship
    ADD  PRIMARY KEY (ship_id)
;



CREATE TABLE Ship_model
(
    ship_model  VARCHAR(150) NOT NULL,
    ship_capacity  INTEGER NOT NULL,
    ship_size  INTEGER NOT NULL
)
;



ALTER TABLE Ship_model
    ADD  PRIMARY KEY (ship_model)
;



CREATE TABLE Store_House
(
    store_id  SERIAL NOT NULL,
    capacity  INTEGER NOT NULL,
    dock_id  INTEGER NOT NULL
)
;



ALTER TABLE Store_House
    ADD  PRIMARY KEY (store_id)
;



CREATE TABLE Team
(
    expirience  INTEGER NOT NULL,
    description  VARCHAR(150) NOT NULL,
    team_id  SERIAL NOT NULL
)
;



ALTER TABLE Team
    ADD  PRIMARY KEY (team_id)
;



CREATE TABLE Team_Member
(
    member_id  SERIAL NOT NULL,
    full_name  VARCHAR(150) NOT NULL,
    position  VARCHAR(150) NOT NULL,
    birth_date  DATE NOT NULL,
    citizenship  VARCHAR(150) NOT NULL,
    team_id  INTEGER NOT NULL
)
;



ALTER TABLE Team_Member
    ADD  PRIMARY KEY (member_id)
;



CREATE TABLE Voyage_Log
(
    log_id  SERIAL NOT NULL,
    ship_id  INTEGER NOT NULL,
    shipment_date  TIMESTAMP NOT NULL
)
;



ALTER TABLE Voyage_Log
    ADD  PRIMARY KEY (log_id)
;



CREATE TABLE Worker
(
    worker_id  SERIAL NOT NULL ,
    hiring_date  DATE NOT NULL,
    position  VARCHAR(150) NOT NULL,
    med_serial_number  VARCHAR(150) NOT NULL ,
    passport_serial_number  VARCHAR(150) NOT NULL ,
    education_serial_number  VARCHAR(150) NOT NULL ,
    employment_serial_number  VARCHAR(150) NOT NULL ,
    dock_id  INTEGER NULL
)
;



ALTER TABLE Worker
    ADD  PRIMARY KEY (worker_id)
;



ALTER TABLE Cargo
    ADD FOREIGN KEY  (customer_id) REFERENCES Customer(customer_id)
;


ALTER TABLE Cargo
    ADD FOREIGN KEY  (freighter_id) REFERENCES Freighter(freighter_id)
;


ALTER TABLE Cargo
    ADD FOREIGN KEY  (order_id) REFERENCES "Order"(order_id)
;



ALTER TABLE Dock
    ADD FOREIGN KEY  (freighter_id) REFERENCES Freighter(freighter_id)
;



ALTER TABLE Freighter_Routes
    ADD FOREIGN KEY  (freighter_id) REFERENCES Freighter(freighter_id)
;


ALTER TABLE Freighter_Routes
    ADD FOREIGN KEY  (route_id) REFERENCES Available_routes(route_id)
;



ALTER TABLE Passport
    ADD FOREIGN KEY  (reg_id) REFERENCES Registration(reg_id)
;



ALTER TABLE Ship
    ADD FOREIGN KEY  (team_id) REFERENCES Team(team_id)
;


ALTER TABLE Ship
    ADD FOREIGN KEY  (ship_model) REFERENCES Ship_model(ship_model)
;


ALTER TABLE Ship
    ADD FOREIGN KEY  (freighter_id) REFERENCES Freighter(freighter_id)
;



ALTER TABLE Store_House
    ADD FOREIGN KEY  (dock_id) REFERENCES Dock(dock_id)
;



ALTER TABLE Team_Member
    ADD FOREIGN KEY  (team_id) REFERENCES Team(team_id)
;



ALTER TABLE Voyage_Log
    ADD FOREIGN KEY  (ship_id) REFERENCES Ship(ship_id)
;



ALTER TABLE Worker
    ADD FOREIGN KEY  (position) REFERENCES Position(position) ON DELETE CASCADE
;


ALTER TABLE Worker
    ADD FOREIGN KEY  (med_serial_number) REFERENCES Medical_card(med_serial_number) ON DELETE CASCADE
;


ALTER TABLE Worker
    ADD FOREIGN KEY  (passport_serial_number) REFERENCES Passport(passport_serial_number) ON DELETE CASCADE
;


ALTER TABLE Worker
    ADD FOREIGN KEY  (education_serial_number) REFERENCES Education(education_serial_number) ON DELETE CASCADE
;


ALTER TABLE Worker
    ADD FOREIGN KEY  (employment_serial_number) REFERENCES Employment(employment_serial_number) ON DELETE CASCADE
;


ALTER TABLE Worker
    ADD FOREIGN KEY  (dock_id) REFERENCES Dock(dock_id)
;