CREATE TABLE filieres
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    code          VARCHAR(255)          NOT NULL,
    nom           VARCHAR(255)          NOT NULL,
    `description` VARCHAR(500)          NULL,
    CONSTRAINT pk_filieres PRIMARY KEY (id)
);

ALTER TABLE filieres
    ADD CONSTRAINT uc_filieres_code UNIQUE (code);