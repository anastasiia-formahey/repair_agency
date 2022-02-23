drop database if exists repair_agency;
create database repair_agency;
use repair_agency;

create table user(
                     login VARCHAR(10) UNIQUE NOT NULL,
                     password VARCHAR(10) NOT NULL,
                     first_name VARCHAR(40) NOT NULL,
                     last_name VARCHAR(40) NOT NULL,
                     email VARCHAR(40) NOT NULL,
                     role VARCHAR(40) NOT NULL,
                     account DOUBLE DEFAULT (0),
                     status VARCHAR(10) DEFAULT ('unblocked'),
                     PRIMARY KEY (login)
);

create table request(
                        id INT NOT NULL AUTO_INCREMENT,
                        description VARCHAR(200) NOT NULL,
                        dateTime DATETIME,
                        price INT DEFAULT 0,
                        status VARCHAR(40) NOT NULL,
                        state VARCHAR(40) NOT NULL,
                        PRIMARY KEY (id)
);
create table user_request(
                             id_request INT NOT NULL,
                             user_login VARCHAR(40) NOT NULL,
                             user_role VARCHAR(40) NOT NULL,
                             FOREIGN KEY (user_login) REFERENCES user(login),
                             FOREIGN KEY (id_request) REFERENCES request(id)
);
create table feedback(
                         id INT NOT NULL AUTO_INCREMENT,
                         description VARCHAR(200) NOT NULL,
                         dateTime DATETIME,
                         id_request INT NOT NULL,
                         master_login VARCHAR(40) NOT NULL,
                         stars INT,
                         PRIMARY KEY (id),
                         FOREIGN KEY (id_request) REFERENCES request(id)
);


insert into user values ('admin', '1234', 'Anastasiia', 'Formahei', 'anastasiia.formahey@gmail.com', 'admin', 1000, default);
insert into user values ('manager1', '1234', 'Anastasiia', 'Formahei', 'anastasiia.formahey@gmail.com', 'manager', 1000, default);

