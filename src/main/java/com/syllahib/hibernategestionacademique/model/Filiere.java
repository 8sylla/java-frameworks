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
 * Entité représentant une Filière académique
 */
@Entity
@Table(name = "filieres")
public class Filiere implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Relation OneToMany avec Eleve
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Eleve> eleves = new ArrayList<>();

    // Relation ManyToMany avec Cours
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "filiere_cours",
            joinColumns = @JoinColumn(name = "filiere_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id")
    )
    private List<Cours> cours = new ArrayList<>();


    // Méthodes utilitaires pour gérer les relations
    public void addEleve(Eleve eleve) {
        eleves.add(eleve);
        eleve.setFiliere(this);
    }

    public void removeEleve(Eleve eleve) {
        eleves.remove(eleve);
        eleve.setFiliere(null);
    }

    public void addCours(Cours coursToAdd) {
        cours.add(coursToAdd);
        coursToAdd.getFilieres().add(this);
    }

    public void removeCours(Cours coursToRemove) {
        cours.remove(coursToRemove);
        coursToRemove.getFilieres().remove(this);
    }

    // equals et hashCode basés sur le code unique
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filiere filiere)) return false;
        return Objects.equals(code, filiere.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Filiere{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
