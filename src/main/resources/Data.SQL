CREATE DATABASE advencedjava;

USE advencedjava;

CREATE TABLE Article
(
    code        VARCHAR(255) NOT NULL,
    designation VARCHAR(255) NULL,
    prix        DOUBLE       NOT NULL,
    CONSTRAINT pk_article PRIMARY KEY (code)
);

INSERT INTO Article VALUES ('ART001','Sac',102.5);
INSERT INTO Article VALUES ('ART002','Bic',10.5);
INSERT INTO Article VALUES ('ART003','Tel',2002.5);
INSERT INTO Article VALUES ('ART004','Ordinateur',10002.5);

show tables ;

SELECT * FROM Article;

