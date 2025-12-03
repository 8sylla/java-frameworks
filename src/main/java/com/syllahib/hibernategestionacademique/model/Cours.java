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
 * Entité représentant un Cours
 */
@Entity
@Table(name = "cours")
public class Cours implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "intitule", nullable = false, length = 100)
    private String intitule;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "credits")
    private Integer credits;

    // Relation ManyToMany avec Filiere
    @ManyToMany(mappedBy = "cours")
    private List<Filiere> filieres = new ArrayList<>();

    // Relation ManyToMany avec Eleve
    @ManyToMany(mappedBy = "cours")
    private List<Eleve> eleves = new ArrayList<>();


    // Méthodes utilitaires
    public void addFiliere(Filiere filiere) {
        filieres.add(filiere);
        filiere.getCours().add(this);
    }

    public void removeFiliere(Filiere filiere) {
        filieres.remove(filiere);
        filiere.getCours().remove(this);
    }

    public void addEleve(Eleve eleve) {
        eleves.add(eleve);
        eleve.getCours().add(this);
    }

    public void removeEleve(Eleve eleve) {
        eleves.remove(eleve);
        eleve.getCours().remove(this);
    }

    // equals et hashCode basés sur le code unique
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cours cours)) return false;
        return Objects.equals(code, cours.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", intitule='" + intitule + '\'' +
                ", credits=" + credits +
                '}';
    }
}