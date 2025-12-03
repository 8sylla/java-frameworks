-- ============================================================================
-- SCRIPTS SQL - GESTION ACADÉMIQUE
-- Base de données MySQL pour l'application de gestion académique
-- ============================================================================

-- ============================================================================
-- 1. CRÉATION DE LA BASE DE DONNÉES
-- ============================================================================

-- Supprimer la base si elle existe déjà (ATTENTION: perte de données!)
-- DROP DATABASE IF EXISTS gestion_academique;

-- Créer la base de données avec encodage UTF-8
CREATE DATABASE IF NOT EXISTS gestion_academique
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Utiliser la base de données
USE gestion_academique;

-- ============================================================================
-- 2. CRÉATION DES UTILISATEURS (Optionnel - pour la sécurité)
-- ============================================================================

-- Créer un utilisateur dédié pour l'application
CREATE USER IF NOT EXISTS 'gestion_user'@'localhost'
IDENTIFIED BY 'Gestion@2024';

-- Accorder tous les privilèges sur la base
GRANT ALL PRIVILEGES ON gestion_academique.*
TO 'gestion_user'@'localhost';

-- Appliquer les changements
FLUSH PRIVILEGES;

-- ============================================================================
-- 3. CRÉATION DES TABLES (Créées automatiquement par Hibernate)
-- ============================================================================

-- Les tables seront créées automatiquement par Hibernate avec:
-- <property name="hibernate.hbm2ddl.auto" value="update"/>

-- Voici la structure qui sera générée:

/*
-- Table: filieres
CREATE TABLE filieres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: eleves
CREATE TABLE eleves (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    matricule VARCHAR(20) NOT NULL UNIQUE,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    filiere_id BIGINT NOT NULL,
    INDEX idx_matricule (matricule),
    INDEX idx_email (email),
    FOREIGN KEY (filiere_id) REFERENCES filieres(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: dossiers_administratifs
CREATE TABLE dossiers_administratifs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_inscription VARCHAR(30) UNIQUE,
    date_creation TIMESTAMP NOT NULL,
    statut VARCHAR(30),
    remarques TEXT,
    eleve_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (eleve_id) REFERENCES eleves(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: cours
CREATE TABLE cours (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    intitule VARCHAR(100) NOT NULL,
    description TEXT,
    credits INT,
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table de jointure: filiere_cours (ManyToMany)
CREATE TABLE filiere_cours (
    filiere_id BIGINT NOT NULL,
    cours_id BIGINT NOT NULL,
    PRIMARY KEY (filiere_id, cours_id),
    FOREIGN KEY (filiere_id) REFERENCES filieres(id) ON DELETE CASCADE,
    FOREIGN KEY (cours_id) REFERENCES cours(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table de jointure: eleve_cours (ManyToMany)
CREATE TABLE eleve_cours (
    eleve_id BIGINT NOT NULL,
    cours_id BIGINT NOT NULL,
    PRIMARY KEY (eleve_id, cours_id),
    FOREIGN KEY (eleve_id) REFERENCES eleves(id) ON DELETE CASCADE,
    FOREIGN KEY (cours_id) REFERENCES cours(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/

-- ============================================================================
-- 4. DONNÉES DE TEST (Optionnel)
-- ============================================================================

-- Insérer des filières de test
INSERT INTO filieres (code, nom, description) VALUES
                                                  ('INF101', 'Informatique', 'Filière en sciences informatiques et développement logiciel'),
                                                  ('MATH101', 'Mathématiques', 'Filière en mathématiques appliquées'),
                                                  ('PHY101', 'Physique', 'Filière en physique et sciences de la matière'),
                                                  ('ELEC101', 'Électronique', 'Filière en électronique et systèmes embarqués');

-- Insérer des élèves de test
INSERT INTO eleves (matricule, nom, prenom, email, filiere_id) VALUES
                                                                   ('E2024001', 'DUPONT', 'Jean', 'jean.dupont@example.com', 1),
                                                                   ('E2024002', 'MARTIN', 'Marie', 'marie.martin@example.com', 1),
                                                                   ('E2024003', 'BERNARD', 'Pierre', 'pierre.bernard@example.com', 2),
                                                                   ('E2024004', 'DUBOIS', 'Sophie', 'sophie.dubois@example.com', 3),
                                                                   ('E2024005', 'RICHARD', 'Thomas', 'thomas.richard@example.com', 1);

-- Insérer des cours de test
INSERT INTO cours (code, intitule, description, credits) VALUES
                                                             ('PROG101', 'Programmation I', 'Introduction à la programmation', 6),
                                                             ('ALGO101', 'Algorithmique', 'Structures de données et algorithmes', 6),
                                                             ('BD101', 'Bases de Données', 'Conception et gestion de bases de données', 5),
                                                             ('WEB101', 'Développement Web', 'HTML, CSS, JavaScript', 5),
                                                             ('MATH201', 'Analyse Mathématique', 'Calcul différentiel et intégral', 7);

-- Associer des cours aux filières
INSERT INTO filiere_cours (filiere_id, cours_id) VALUES
                                                     (1, 1), -- Informatique - Programmation I
                                                     (1, 2), -- Informatique - Algorithmique
                                                     (1, 3), -- Informatique - Bases de Données
                                                     (1, 4), -- Informatique - Développement Web
                                                     (2, 5); -- Mathématiques - Analyse

-- Inscrire des élèves aux cours
INSERT INTO eleve_cours (eleve_id, cours_id) VALUES
                                                 (1, 1), -- Jean DUPONT - Programmation I
                                                 (1, 2), -- Jean DUPONT - Algorithmique
                                                 (2, 1), -- Marie MARTIN - Programmation I
                                                 (2, 3), -- Marie MARTIN - Bases de Données
                                                 (3, 5); -- Pierre BERNARD - Analyse

-- Créer des dossiers administratifs
INSERT INTO dossiers_administratifs
(numero_inscription, date_creation, statut, remarques, eleve_id) VALUES
                                                                     ('INS-2024001', NOW(), 'ACTIF', 'Dossier complet', 1),
                                                                     ('INS-2024002', NOW(), 'ACTIF', 'En attente de documents', 2),
                                                                     ('INS-2024003', NOW(), 'ACTIF', 'Dossier validé', 3);

-- ============================================================================
-- 5. REQUÊTES UTILES POUR LES TESTS
-- ============================================================================

-- Afficher toutes les filières avec le nombre d'élèves
SELECT
    f.code,
    f.nom,
    COUNT(e.id) as nombre_eleves
FROM filieres f
         LEFT JOIN eleves e ON f.id = e.filiere_id
GROUP BY f.id, f.code, f.nom;

-- Afficher tous les élèves avec leur filière
SELECT
    e.matricule,
    e.nom,
    e.prenom,
    e.email,
    f.nom as filiere
FROM eleves e
         JOIN filieres f ON e.filiere_id = f.id
ORDER BY e.nom, e.prenom;

-- Afficher les cours d'une filière spécifique
SELECT
    c.code,
    c.intitule,
    c.credits
FROM cours c
         JOIN filiere_cours fc ON c.id = fc.cours_id
         JOIN filieres f ON fc.filiere_id = f.id
WHERE f.code = 'INF101';

-- Afficher les élèves inscrits à un cours
SELECT
    e.matricule,
    e.nom,
    e.prenom,
    c.intitule as cours
FROM eleves e
         JOIN eleve_cours ec ON e.id = ec.eleve_id
         JOIN cours c ON ec.cours_id = c.cours_id
WHERE c.code = 'PROG101';

-- Afficher les dossiers administratifs avec les informations des élèves
SELECT
    d.numero_inscription,
    d.date_creation,
    d.statut,
    e.matricule,
    e.nom,
    e.prenom
FROM dossiers_administratifs d
         JOIN eleves e ON d.eleve_id = e.id;

-- ============================================================================
-- 6. REQUÊTES DE MAINTENANCE
-- ============================================================================

-- Vérifier les contraintes d'intégrité
SELECT
    TABLE_NAME,
    CONSTRAINT_NAME,
    CONSTRAINT_TYPE
FROM information_schema.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = 'gestion_academique';

-- Afficher la taille de la base de données
SELECT
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.TABLES
WHERE table_schema = 'gestion_academique';

-- Compter les enregistrements dans chaque table
SELECT
    'filieres' as table_name,
    COUNT(*) as count FROM filieres
UNION ALL
SELECT
    'eleves',
    COUNT(*) FROM eleves
UNION ALL
SELECT
    'cours',
    COUNT(*) FROM cours
UNION ALL
SELECT
    'dossiers_administratifs',
    COUNT(*) FROM dossiers_administratifs;

-- ============================================================================
-- 7. NETTOYAGE (À utiliser avec précaution!)
-- ============================================================================

-- Vider toutes les tables (dans l'ordre pour respecter les contraintes)
/*
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE eleve_cours;
TRUNCATE TABLE filiere_cours;
TRUNCATE TABLE dossiers_administratifs;
TRUNCATE TABLE eleves;
TRUNCATE TABLE cours;
TRUNCATE TABLE filieres;
SET FOREIGN_KEY_CHECKS = 1;
*/

-- Supprimer toutes les tables
/*
DROP TABLE IF EXISTS eleve_cours;
DROP TABLE IF EXISTS filiere_cours;
DROP TABLE IF EXISTS dossiers_administratifs;
DROP TABLE IF EXISTS eleves;
DROP TABLE IF EXISTS cours;
DROP TABLE IF EXISTS filieres;
*/

-- ============================================================================
-- FIN DU SCRIPT
-- ============================================================================