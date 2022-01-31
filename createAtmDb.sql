--CREATE TABLES

CREATE TABLE IF NOT EXISTS User (
UserId INT(6) UNSIGNED AUTO_INCREMENT,
UserFirstName VARCHAR(30) NOT NULL,
UserLastName VARCHAR(30) NOT NULL,
UserLogin VARCHAR(30) NOT NULL,
UserPin INT(4) NOT NULL,
UserAccountBalance DOUBLE(40,2) NOT NULL,
PRIMARY KEY(UserId)
);

CREATE TABLE IF NOT EXISTS Transaction (
TransactionId INT(6) UNSIGNED AUTO_INCREMENT,
TransactionValue DOUBLE(40,2) NOT NULL,
TransactionType VARCHAR(20) NOT NULL,
TransactionDate DATE,
UserId INT(6) UNSIGNED NOT NULL,
PRIMARY KEY(TransactionId),
FOREIGN KEY(UserId) References User(UserId)
);

--INSERT INITIAL VALUES

INSERT INTO User (UserId, UserFirstName, UserLastName, UserLogin, UserPin, UserAccountBalance)
VALUES(1, "Jan", "Kowalski", "janek", 6969, 350.00),
(2, "Anna", "Gwiazdeczka", "olajwa", 1111, 1282.00),
(3, "Janusz", "Nowak", "nowy", 1234, 590.00);

INSERT INTO Transaction(TransactionValue, TransactionType, TransactionDate, UserId)
VALUES(100.00, "MONEY_WITHDRAW", '2020-01-26', 2);
INSERT INTO Transaction(TransactionValue, TransactionType, TransactionDate, UserId)
VALUES(200.00, "MONEY_WITHDRAW", '2020-01-28', 2);