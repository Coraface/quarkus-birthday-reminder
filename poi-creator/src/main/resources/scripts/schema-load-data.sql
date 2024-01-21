CREATE sequence Person_SEQ start with 1 increment by 50;

CREATE TABLE Person (
    id bigint NOT NULL,
    age integer NOT NULL,
    birthday varchar(255),
    firstName varchar(255),
    lastName varchar(255),
    primary key (id)
);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (1, 'Lina', 'Chorafa', '09/06/2005', 18);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (2, 'Sofia', 'Chorafa', '27/08/1994', 29);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (3, 'Anthoula', 'Mhtkoudh', '08/08/1970', 53);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (4, 'Giannis', 'Chorafas', '27/02/1959', 64);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (7, 'Vasilis', 'Mhtkoudhs', '08/04/2000', 23);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (6, 'Christos', 'Mhtkoudhs', '25/11/1997', 26);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (5, 'Eva', 'Nissopoulou', '12/12/1992', 31);
INSERT INTO person (id, firstname, lastname, birthday, age) VALUES (8, 'Elenh', 'Matzara', '10/07/1997', 26);
ALTER SEQUENCE person_seq RESTART WITH 9;
