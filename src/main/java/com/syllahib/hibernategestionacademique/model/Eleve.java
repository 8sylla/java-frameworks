package com.syllahib.hibernategestionacademique.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor

/**
 * Entité représentant un Élève
 */
@Entity
@Table(name = "eleves")
public class Eleve implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule", nullable = false, unique = true, length = 20)
    private String matricule;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // Relation ManyToOne avec Filiere
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;

    // Relation OneToOne avec DossierAdministratif
    @OneToOne(mappedBy = "eleve", cascade = CascadeType.ALL, orphanRemoval = true)
    private DossierAdministratif dossierAdministratif;

    // Relation ManyToMany avec Cours
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "eleve_cours",
            joinColumns = @JoinColumn(name = "eleve_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id")
    )
    private List<Cours> cours = new ArrayList<>();


    // Méthodes utilitaires
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public void addCours(Cours coursToAdd) {
        cours.add(coursToAdd);
        coursToAdd.getEleves().add(this);
    }

    public void removeCours(Cours coursToRemove) {
        cours.remove(coursToRemove);
        coursToRemove.getEleves().remove(this);
    }

    // equals et hashCode basés sur le matricule unique
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eleve eleve)) return false;
        return Objects.equals(matricule, eleve.matricule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule);
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", filiere=" + (filiere != null ? filiere.getCode() : "null") +
                '}';
    }
}