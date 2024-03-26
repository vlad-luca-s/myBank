Create a database named 'mybank', then create a table and insert at least 2 records.

CREATE TABLE Bank_Account (
id int NOT null,
Account_Number VARCHAR(12),
Balance DECIMAL(10, 2)
);

INSERT INTO bank_account VALUES (1, '10001', 500.75);
INSERT INTO bank_account VALUES (2, '20002', 300.5);