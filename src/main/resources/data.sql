DELETE FROM ACCOUNT;
INSERT INTO
    ACCOUNT (forename, surname, username, email, password, role)
VALUES
    ('forename', 'surname', 'username', 'email', '{noop}password', 'A');
INSERT INTO
    ACCOUNT (forename, surname, username, email, password, role)
VALUES
    ('John', 'Doe', 'johndoe', 'john.doe@email.com', '{noop}john', 'I');
INSERT INTO
    ACCOUNT (forename, surname, username, email, password, role)
VALUES
    ('Jane', 'Smith', 'janesmith', 'jane.smith@email.com', '{noop}jane', 'S');
