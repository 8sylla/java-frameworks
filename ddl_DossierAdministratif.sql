CREATE TABLE dossiers_administratifs
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    date_creation datetime              NOT NULL,
    eleve_id      BIGINT                NULL,
    CONSTRAINT pk_dossiers_administratifs PRIMARY KEY (id)
);

ALTER TABLE dossiers_administratifs
    ADD CONSTRAINT uc_dossiers_administratifs_eleve UNIQUE (eleve_id);

ALTER TABLE dossiers_administratifs
    ADD CONSTRAINT FK_DOSSIERS_ADMINISTRATIFS_ON_ELEVE FOREIGN KEY (eleve_id) REFERENCES eleves (id);