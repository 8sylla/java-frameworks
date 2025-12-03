package com.syllahib.hibernategestionacademique.service;

import com.syllahib.hibernategestionacademique.dao.DossierAdministratifDAO;
import com.syllahib.hibernategestionacademique.dao.EleveDAO;
import com.syllahib.hibernategestionacademique.model.DossierAdministratif;
import com.syllahib.hibernategestionacademique.model.Eleve;

import java.util.List;

/**
 * Service pour gérer la logique métier des Dossiers Administratifs
 */
public class DossierAdministratifService {

    private DossierAdministratifDAO dossierDAO;
    private EleveDAO eleveDAO;

    public DossierAdministratifService() {
        this.dossierDAO = new DossierAdministratifDAO();
        this.eleveDAO = new EleveDAO();
    }

    /**
     * Créer un nouveau dossier administratif
     */
    public DossierAdministratif createDossier(DossierAdministratif dossier) throws Exception {
        validateDossier(dossier);

        // Vérifier que l'élève existe
        if (dossier.getEleve() != null && dossier.getEleve().getId() != null) {
            Eleve eleve = eleveDAO.findById(dossier.getEleve().getId());
            if (eleve == null) {
                throw new Exception("L'élève spécifié n'existe pas");
            }

            // Vérifier que l'élève n'a pas déjà un dossier
            if (dossierDAO.eleveHasDossier(eleve.getId())) {
                throw new Exception("Cet élève a déjà un dossier administratif");
            }

            dossier.setEleve(eleve);
        }

        return dossierDAO.create(dossier);
    }

    /**
     * Mettre à jour un dossier administratif
     */
    public DossierAdministratif updateDossier(DossierAdministratif dossier) throws Exception {
        validateDossier(dossier);

        if (dossier.getId() == null) {
            throw new Exception("L'ID du dossier ne peut pas être null");
        }

        return dossierDAO.update(dossier);
    }

    /**
     * Supprimer un dossier administratif
     */
    public boolean deleteDossier(Long id) throws Exception {
        DossierAdministratif dossier = dossierDAO.findById(id);
        if (dossier == null) {
            throw new Exception("Dossier non trouvé");
        }

        return dossierDAO.delete(id);
    }

    /**
     * Trouver un dossier par ID
     */
    public DossierAdministratif findById(Long id) {
        return dossierDAO.findById(id);
    }

    /**
     * Trouver un dossier par ID d'élève
     */
    public DossierAdministratif findByEleveId(Long eleveId) {
        return dossierDAO.findByEleveId(eleveId);
    }

    /**
     * Trouver un dossier par numéro d'inscription
     */
    public DossierAdministratif findByNumeroInscription(String numeroInscription) {
        return dossierDAO.findByNumeroInscription(numeroInscription);
    }

    /**
     * Récupérer tous les dossiers
     */
    public List<DossierAdministratif> findAll() {
        return dossierDAO.findAll();
    }

    /**
     * Compter le nombre de dossiers
     */
    public long count() {
        return dossierDAO.count();
    }

    /**
     * Valider les données d'un dossier
     */
    private void validateDossier(DossierAdministratif dossier) throws Exception {
        if (dossier == null) {
            throw new Exception("Le dossier ne peut pas être null");
        }

        if (dossier.getEleve() == null) {
            throw new Exception("L'élève est obligatoire");
        }
    }
}