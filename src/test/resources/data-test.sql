DELETE FROM ENDUSERS;
INSERT INTO
    ENDUSERS (ENDUSER_TYPE, ID, forename, surname, username, email, password, role)
VALUES
    ('A', 1, 'forename', 'surname', 'username', 'email@email.com', '{noop}password', 'A');
INSERT INTO
    ENDUSERS (ENDUSER_TYPE, ID, forename, surname, username, email, password, role)
VALUES
    ('I', 2,'John', 'Doe', 'johndoe', 'john.doe@email.com', '{noop}john', 'I');
INSERT INTO
    ENDUSERS (ENDUSER_TYPE, ID, forename, surname, username, email, password, role)
VALUES
    ('S', 3, 'Jane', 'Smith', 'janesmith', 'jane.smith@email.com', '{noop}jane', 'S');
INSERT INTO
    ENDUSERS (ENDUSER_TYPE, ID, forename, surname, username, email, password, role)
VALUES
    ('A', 4,'dummyForename', 'dummySurname', 'dummyUsername', 'email@email.com', '{noop}password', 'A');
