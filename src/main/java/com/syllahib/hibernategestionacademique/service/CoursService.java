package com.syllahib.hibernategestionacademique.service;

import com.syllahib.hibernategestionacademique.dao.CoursDAO;
import com.syllahib.hibernategestionacademique.model.Cours;

import java.util.List;

/**
 * Service pour gérer la logique métier des Cours
 */
public class CoursService {

    private CoursDAO coursDAO;

    public CoursService() {
        this.coursDAO = new CoursDAO();
    }

    /**
     * Créer un nouveau cours avec validation
     */
    public Cours createCours(Cours cours) throws Exception {
        validateCours(cours);

        if (coursDAO.codeExists(cours.getCode())) {
            throw new Exception("Le code " + cours.getCode() + " existe déjà");
        }

        return coursDAO.create(cours);
    }

    /**
     * Mettre à jour un cours
     */
    public Cours updateCours(Cours cours) throws Exception {
        validateCours(cours);

        if (cours.getId() == null) {
            throw new Exception("L'ID du cours ne peut pas être null");
        }

        if (coursDAO.codeExistsForOther(cours.getCode(), cours.getId())) {
            throw new Exception("Le code " + cours.getCode() + " est déjà utilisé");
        }

        return coursDAO.update(cours);
    }

    /**
     * Supprimer un cours
     */
    public boolean deleteCours(Long id) throws Exception {
        Cours cours = coursDAO.findById(id);
        if (cours == null) {
            throw new Exception("Cours non trouvé");
        }

        return coursDAO.delete(id);
    }

    /**
     * Trouver un cours par ID
     */
    public Cours findById(Long id) {
        return coursDAO.findById(id);
    }

    /**
     * Trouver un cours par code
     */
    public Cours findByCode(String code) {
        return coursDAO.findByCode(code);
    }

    /**
     * Récupérer tous les cours
     */
    public List<Cours> findAll() {
        return coursDAO.findAll();
    }

    /**
     * Trouver les cours d'une filière
     */
    public List<Cours> findByFiliere(Long filiereId) {
        return coursDAO.findByFiliere(filiereId);
    }

    /**
     * Trouver les cours d'un élève
     */
    public List<Cours> findByEleve(Long eleveId) {
        return coursDAO.findByEleve(eleveId);
    }

    /**
     * Compter le nombre de cours
     */
    public long count() {
        return coursDAO.count();
    }

    /**
     * Valider les données d'un cours
     */
    private void validateCours(Cours cours) throws Exception {
        if (cours == null) {
            throw new Exception("Le cours ne peut pas être null");
        }

        if (cours.getCode() == null || cours.getCode().trim().isEmpty()) {
            throw new Exception("Le code du cours est obligatoire");
        }

        if (cours.getIntitule() == null || cours.getIntitule().trim().isEmpty()) {
            throw new Exception("L'intitulé du cours est obligatoire");
        }

        if (cours.getCode().length() > 20) {
            throw new Exception("Le code ne peut pas dépasser 20 caractères");
        }

        if (cours.getIntitule().length() > 100) {
            throw new Exception("L'intitulé ne peut pas dépasser 100 caractères");
        }
    }
}
