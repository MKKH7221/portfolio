CREATE DATABASE IF NOT EXISTS testdb;
DROP TABLE IF EXISTS testdb.user CASCADE;

DROP TABLE testdb.user;
CREATE TABLE testdb.user(
        id              integer  AUTO_INCREMENT,
        name            varchar(20) not null,
        address         varchar(100) not null,
        tel             varchar(100) not null,
        country         varchar(20) not null,
        update_date       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        created_date      DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Tarou', 'via tokyo 234','3456789012','JPN');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Jirou','via tokyo 234','3456789012','JPN');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Saburo','via tokyo 234','1239384811','GRC');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Shirou','via tokyo 234','1239384811','ESP');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Gorou','via tokyo 234','1239384811','ITA');



