package com.syllahib.hibernategestionacademique.service;

import com.syllahib.hibernategestionacademique.dao.FiliereDAO;
import com.syllahib.hibernategestionacademique.model.Filiere;

import java.util.List;

/**
 * Service pour gérer la logique métier des Filières
 */
public class FiliereService {

    private FiliereDAO filiereDAO;

    public FiliereService() {
        this.filiereDAO = new FiliereDAO();
    }

    /**
     * Créer une nouvelle filière avec validation
     */
    public Filiere createFiliere(Filiere filiere) throws Exception {
        // Validation
        validateFiliere(filiere);

        // Vérifier si le code existe déjà
        if (filiereDAO.codeExists(filiere.getCode())) {
            throw new Exception("Le code " + filiere.getCode() + " existe déjà");
        }

        return filiereDAO.create(filiere);
    }

    /**
     * Mettre à jour une filière
     */
    public Filiere updateFiliere(Filiere filiere) throws Exception {
        // Validation
        validateFiliere(filiere);

        if (filiere.getId() == null) {
            throw new Exception("L'ID de la filière ne peut pas être null");
        }

        // Vérifier si le code existe pour une autre filière
        if (filiereDAO.codeExistsForOther(filiere.getCode(), filiere.getId())) {
            throw new Exception("Le code " + filiere.getCode() + " est déjà utilisé par une autre filière");
        }

        return filiereDAO.update(filiere);
    }

    /**
     * Supprimer une filière
     */
    public boolean deleteFiliere(Long id) throws Exception {
        Filiere filiere = filiereDAO.findById(id);
        if (filiere == null) {
            throw new Exception("Filière non trouvée");
        }

        // Vérifier si des élèves sont inscrits
        if (!filiere.getEleves().isEmpty()) {
            throw new Exception("Impossible de supprimer la filière car des élèves y sont inscrits");
        }

        return filiereDAO.delete(id);
    }

    /**
     * Trouver une filière par ID
     */
    public Filiere findById(Long id) {
        return filiereDAO.findById(id);
    }

    /**
     * Trouver une filière par code
     */
    public Filiere findByCode(String code) {
        return filiereDAO.findByCode(code);
    }

    /**
     * Récupérer toutes les filières
     */
    public List<Filiere> findAll() {
        return filiereDAO.findAll();
    }

    /**
     * Compter le nombre de filières
     */
    public long count() {
        return filiereDAO.count();
    }

    /**
     * Valider les données d'une filière
     */
    private void validateFiliere(Filiere filiere) throws Exception {
        if (filiere == null) {
            throw new Exception("La filière ne peut pas être null");
        }

        if (filiere.getCode() == null || filiere.getCode().trim().isEmpty()) {
            throw new Exception("Le code de la filière est obligatoire");
        }

        if (filiere.getNom() == null || filiere.getNom().trim().isEmpty()) {
            throw new Exception("Le nom de la filière est obligatoire");
        }

        if (filiere.getCode().length() > 20) {
            throw new Exception("Le code ne peut pas dépasser 20 caractères");
        }

        if (filiere.getNom().length() > 100) {
            throw new Exception("Le nom ne peut pas dépasser 100 caractères");
        }
    }
}
