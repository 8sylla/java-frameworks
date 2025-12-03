CREATE TABLE cours
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    code     VARCHAR(255)          NOT NULL,
    intitule VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_cours PRIMARY KEY (id)
);

CREATE TABLE cours_eleve
(
    cours_id BIGINT NOT NULL,
    eleve_id BIGINT NOT NULL
);

CREATE TABLE cours_filiere
(
    cours_id   BIGINT NOT NULL,
    filiere_id BIGINT NOT NULL
);

ALTER TABLE cours
    ADD CONSTRAINT uc_cours_code UNIQUE (code);

ALTER TABLE cours_eleve
    ADD CONSTRAINT fk_couele_on_cours FOREIGN KEY (cours_id) REFERENCES cours (id);

ALTER TABLE cours_eleve
    ADD CONSTRAINT fk_couele_on_eleve FOREIGN KEY (eleve_id) REFERENCES eleves (id);

ALTER TABLE cours_filiere
    ADD CONSTRAINT fk_coufil_on_cours FOREIGN KEY (cours_id) REFERENCES cours (id);

ALTER TABLE cours_filiere
    ADD CONSTRAINT fk_coufil_on_filiere FOREIGN KEY (filiere_id) REFERENCES filieres (id);

