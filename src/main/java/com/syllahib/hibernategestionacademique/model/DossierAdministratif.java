package com.syllahib.hibernategestionacademique.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor

/**
 * Entité représentant un Dossier Administratif
 */
@Entity
@Table(name = "dossiers_administratifs")
public class DossierAdministratif implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_inscription", unique = true, length = 30)
    private String numeroInscription;

    @Column(name = "date_creation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Column(name = "statut", length = 30)
    private String statut; // ACTIF, INACTIF, EN_ATTENTE

    @Column(name = "remarques", columnDefinition = "TEXT")
    private String remarques;

    // Relation OneToOne avec Eleve
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eleve_id", nullable = false, unique = true)
    private Eleve eleve;


    // Méthode pour générer automatiquement un numéro d'inscription
    @PrePersist
    public void generateNumeroInscription() {
        if (this.numeroInscription == null && this.eleve != null) {
            this.numeroInscription = "INS-" + System.currentTimeMillis();
        }
        if (this.dateCreation == null) {
            this.dateCreation = new Date();
        }
    }

    // equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DossierAdministratif that)) return false;
        return Objects.equals(numeroInscription, that.numeroInscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroInscription);
    }

    @Override
    public String toString() {
        return "DossierAdministratif{" +
                "id=" + id +
                ", numeroInscription='" + numeroInscription + '\'' +
                ", dateCreation=" + dateCreation +
                ", statut='" + statut + '\'' +
                ", eleve=" + (eleve != null ? eleve.getMatricule() : "null") +
                '}';
    }
}