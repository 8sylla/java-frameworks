CREATE TABLE eleves
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    matricule  VARCHAR(255)          NOT NULL,
    nom        VARCHAR(255)          NOT NULL,
    prenom     VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    filiere_id BIGINT                NULL,
    CONSTRAINT pk_eleves PRIMARY KEY (id)
);

ALTER TABLE eleves
    ADD CONSTRAINT uc_eleves_matricule UNIQUE (matricule);

ALTER TABLE eleves
    ADD CONSTRAINT FK_ELEVES_ON_FILIERE FOREIGN KEY (filiere_id) REFERENCES filieres (id);