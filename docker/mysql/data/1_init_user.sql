CREATE DATABASE IF NOT EXISTS testdb;
DROP TABLE IF EXISTS testdb.use;

CREATE TABLE testdb.user(
        id              integer  AUTO_INCREMENT,
        name            varchar(30) not null,
        address         varchar(100) not null,
        tel             varchar(100) not null,
        country         varchar(20) not null,
        update_date       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        created_date      DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

ALTER TABLE testdb.user AUTO_INCREMENT = 1;

INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Tarou Itou', '3-1 Kanda, Chiyodaku, Tokyo','3456789012','JPN');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Jirou Tanaka','5-21 Higashihatsuishi, Nagareyama, Chiba','3456789012','JPN');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Shah Rukh Khan','Mannat, Bandra West, Mumbai, Maharashtra 400050, India','1239384811','IND');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Deepika Padukone','Beaumonde Towers, 16th Floor, Prabhadevi, Mumbai, Maharashtra 400025, India','1239384811','IND');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Priyanka Chopra','355, Carter Road, Bandra West, Mumbai, Maharashtra 400050, India','1239384811','IND');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Akshay Kumar','Prime Beach Apartments, Juhu, Mumbai, Maharashtra 400049, India','1239384811','IND');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Gaio Giulio Cesare','Piazza Navona, 00186 Roma RM','1239384811','ITA');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Leonardo da Vinci','Piazza del Duomo, 50122 Firenze FI','1239384811','ITA');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Raffaello Sanzio','Piazza di Santa Maria in Trastevere, 00153 Roma RM, Italia','1239384811','ITA');
INSERT INTO testdb.user( name, address, tel, country ) VALUES ('Sandro Botticelli','Via del Corso, 437, 00186 Roma RM, Italia','1239384811','ITA');



