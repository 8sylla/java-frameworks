# 🎓 Gestion Académique - Application MVC avec Hibernate

## 📋 Description du Projet

Application web de gestion académique développée avec **Hibernate pur**, **JSP/JSTL** et architecture **MVC**. Cette application permet de gérer les filières, élèves, cours et dossiers administratifs.

## 🏗️ Architecture

### Structure MVC Simplifiée
```
gestion-academique/
├── src/main/java/com/gestion/
│   ├── model/              # Entités JPA
│   │   ├── Filiere.java
│   │   ├── Eleve.java
│   │   ├── DossierAdministratif.java
│   │   └── Cours.java
│   ├── dao/                # Data Access Objects
│   │   ├── CRUD.java       # Interface générique
│   │   ├── GenericDAO.java
│   │   ├── FiliereDAO.java
│   │   ├── EleveDAO.java
│   │   ├── DossierAdministratifDAO.java
│   │   └── CoursDAO.java
│   ├── service/            # Logique métier
│   │   ├── FiliereService.java
│   │   ├── EleveService.java
│   │   ├── DossierAdministratifService.java
│   │   └── CoursService.java
│   ├── controller/         # Servlets
│   │   ├── FiliereController.java
│   │   ├── EleveController.java
│   │   ├── DossierAdministratifController.java
│   │   └── CoursController.java
│   └── util/
│       └── HibernateUtil.java
├── src/main/resources/
│   └── META-INF/
│       └── persistence.xml
└── src/main/webapp/
    ├── WEB-INF/
    │   ├── web.xml
    │   └── views/
    │       ├── filiere/
    │       │   ├── list.jsp
    │       │   └── form.jsp
    │       ├── eleve/
    │       │   ├── list.jsp
    │       │   └── form.jsp
    │       ├── cours/
    │       │   ├── list.jsp
    │       │   └── form.jsp
    │       └── dossier/
    │           ├── list.jsp
    │           └── form.jsp
    └── index.jsp
```

## 🔗 Relations entre Entités

```
Filiere 1 ---> * Eleve (OneToMany)
Eleve * ---> 1 Filiere (ManyToOne)
Eleve 1 ---> 1 DossierAdministratif (OneToOne)
Filiere * <---> * Cours (ManyToMany)
Eleve * <---> * Cours (ManyToMany)
```

## 🛠️ Technologies Utilisées

- **Java 11**
- **Hibernate 5.6.15.Final** (JPA)
- **MySQL 8.0**
- **Servlet API 4.0**
- **JSP/JSTL 1.2**
- **Maven 3.x**
- **Apache Tomcat 9.x**

## 📦 Prérequis

1. **JDK 11** ou supérieur
2. **Apache Maven 3.6+**
3. **MySQL 8.0+**
4. **Apache Tomcat 9.0+**
5. **IDE** (Eclipse, IntelliJ IDEA, NetBeans)

## 🚀 Installation et Configuration

### Étape 1: Cloner le projet
```bash
git clone [url-du-projet]
cd gestion-academique
```

### Étape 2: Configurer la base de données MySQL

```sql
CREATE DATABASE gestion_academique CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'gestion_user'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
GRANT ALL PRIVILEGES ON gestion_academique.* TO 'gestion_user'@'localhost';
FLUSH PRIVILEGES;
```

### Étape 3: Modifier persistence.xml

Ouvrez `src/main/resources/META-INF/persistence.xml` et modifiez:
```xml
<property name="javax.persistence.jdbc.user" value="gestion_user"/>
<property name="javax.persistence.jdbc.password" value="votre_mot_de_passe"/>
```

### Étape 4: Compiler le projet

```bash
mvn clean package
```

### Étape 5: Déployer sur Tomcat

1. Copiez le fichier WAR généré: `target/gestion-academique.war`
2. Collez-le dans le dossier `webapps` de Tomcat
3. Démarrez Tomcat

Ou directement depuis votre IDE en configurant un serveur Tomcat.

### Étape 6: Accéder à l'application

Ouvrez votre navigateur: `http://localhost:8080/gestion-academique/`

## 📝 Fonctionnalités

### Module Filières
- ✅ Créer une nouvelle filière
- ✅ Lister toutes les filières
- ✅ Modifier une filière existante
- ✅ Supprimer une filière (si aucun élève inscrit)
- ✅ Recherche par code unique

### Module Élèves
- ✅ Créer un nouvel élève
- ✅ Lister tous les élèves
- ✅ Filtrer par filière
- ✅ Modifier un élève
- ✅ Supprimer un élève
- ✅ Validation matricule et email uniques

### Module Cours
- ✅ Créer un nouveau cours
- ✅ Lister tous les cours
- ✅ Modifier un cours
- ✅ Supprimer un cours
- ✅ Gestion des crédits

### Module Dossiers Administratifs
- ✅ Créer un dossier pour un élève
- ✅ Lister tous les dossiers
- ✅ Modifier le statut d'un dossier
- ✅ Génération automatique du numéro d'inscription

## 🧪 Tests Manuels

### Test 1: Création d'une Filière
1. Accéder à "Filières" → "Nouvelle Filière"
2. Remplir le formulaire:
    - Code: INF101
    - Nom: Informatique
    - Description: Filière informatique générale
3. Cliquer sur "Créer"
4. Vérifier le message de succès
5. **Capture d'écran**: Liste des filières avec la nouvelle filière

### Test 2: Création d'un Élève
1. Accéder à "Élèves" → "Nouvel Élève"
2. Remplir le formulaire:
    - Matricule: E2024001
    - Nom: DUPONT
    - Prénom: Jean
    - Email: jean.dupont@example.com
    - Filière: Informatique
3. Cliquer sur "Créer"
4. **Capture d'écran**: Liste des élèves

### Test 3: Modification d'un Élève
1. Dans la liste des élèves, cliquer sur "Modifier"
2. Changer l'email
3. Valider
4. **Capture d'écran**: Message de succès

### Test 4: Suppression avec Contrainte
1. Essayer de supprimer une filière avec des élèves inscrits
2. Vérifier le message d'erreur
3. **Capture d'écran**: Message d'erreur

### Test 5: Filtrage
1. Créer plusieurs élèves dans différentes filières
2. Utiliser le filtre par filière
3. **Capture d'écran**: Résultat du filtrage

### Test 6: Validation des Doublons
1. Essayer de créer un élève avec un matricule existant
2. Vérifier le message d'erreur
3. **Capture d'écran**: Message d'erreur de validation

## 📊 Diagramme de Classes

```
┌─────────────────┐         ┌──────────────────┐
│    Filiere      │1      * │      Eleve       │
│─────────────────│◄────────│──────────────────│
│ +id: Long       │         │ +id: Long        │
│ +code: String   │         │ +matricule: Str  │
│ +nom: String    │         │ +nom: String     │
│ +description    │         │ +prenom: String  │
└─────────────────┘         │ +email: String   │
                            └──────────────────┘
                                    │1
                                    │
                                    │1
                            ┌───────▼──────────┐
                            │ DossierAdmin     │
                            │──────────────────│
                            │ +id: Long        │
                            │ +numeroInscr.    │
                            │ +dateCreation    │
                            └──────────────────┘
```

## 🔍 Points Importants

### Best Practices Implémentées

1. **Séparation des Responsabilités**: Couches Model-DAO-Service-Controller-View
2. **Interface CRUD Générique**: Réutilisabilité du code
3. **Validation des Données**: Côté service et base de données
4. **Gestion des Transactions**: Commit/Rollback automatique
5. **Pattern Singleton**: Pour EntityManagerFactory
6. **Messages Utilisateur**: Feedback clair (succès/erreur)
7. **Design Responsive**: Interface moderne et intuitive

### Contraintes d'Intégrité

- Code unique pour Filière
- Matricule unique pour Élève
- Email unique pour Élève
- Une filière ne peut être supprimée si elle a des élèves
- Relation OneToOne stricte entre Élève et Dossier

## 🐛 Troubleshooting

### Problème: Erreur de connexion à la base
**Solution**: Vérifiez les paramètres dans `persistence.xml`

### Problème: ClassNotFoundException
**Solution**: Vérifiez que toutes les dépendances Maven sont téléchargées

### Problème: 404 Not Found
**Solution**: Vérifiez le context path de Tomcat

## 👨‍💻 Auteur

Projet académique - MVC avec Hibernate pur

## 📄 Licence

Ce projet est à usage éducatif uniquement.

---

**Note**: N'oubliez pas de prendre des captures d'écran pour chaque test effectué et de les inclure dans votre livrable PDF.