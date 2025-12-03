package com.syllahib.hibernategestionacademique.service;

import com.syllahib.hibernategestionacademique.dao.EleveDAO;
import com.syllahib.hibernategestionacademique.dao.FiliereDAO;
import com.syllahib.hibernategestionacademique.model.Eleve;
import com.syllahib.hibernategestionacademique.model.Filiere;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service pour gérer la logique métier des Élèves
 */
public class EleveService {

    private final EleveDAO eleveDAO;
    private final FiliereDAO filiereDAO;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public EleveService() {
        this.eleveDAO = new EleveDAO();
        this.filiereDAO = new FiliereDAO();
    }

    /**
     * Créer un nouvel élève avec validation
     */
    public Eleve createEleve(Eleve eleve) throws Exception {
        // Validation
        validateEleve(eleve);

        // Vérifier si le matricule existe déjà
        if (eleveDAO.matriculeExists(eleve.getMatricule())) {
            throw new Exception("Le matricule " + eleve.getMatricule() + " existe déjà");
        }

        // Vérifier si l'email existe déjà
        if (eleveDAO.emailExists(eleve.getEmail())) {
            throw new Exception("L'email " + eleve.getEmail() + " existe déjà");
        }

        // Vérifier que la filière existe
        if (eleve.getFiliere() != null && eleve.getFiliere().getId() != null) {
            Filiere filiere = filiereDAO.findById(eleve.getFiliere().getId());
            if (filiere == null) {
                throw new Exception("La filière spécifiée n'existe pas");
            }
            eleve.setFiliere(filiere);
        }

        return eleveDAO.create(eleve);
    }

    /**
     * Mettre à jour un élève
     */
    public Eleve updateEleve(Eleve eleve) throws Exception {
        // Validation
        validateEleve(eleve);

        if (eleve.getId() == null) {
            throw new Exception("L'ID de l'élève ne peut pas être null");
        }

        // Vérifier si le matricule existe pour un autre élève
        if (eleveDAO.matriculeExistsForOther(eleve.getMatricule(), eleve.getId())) {
            throw new Exception("Le matricule " + eleve.getMatricule() + " est déjà utilisé");
        }

        // Vérifier si l'email existe pour un autre élève
        if (eleveDAO.emailExistsForOther(eleve.getEmail(), eleve.getId())) {
            throw new Exception("L'email " + eleve.getEmail() + " est déjà utilisé");
        }

        // Vérifier que la filière existe
        if (eleve.getFiliere() != null && eleve.getFiliere().getId() != null) {
            Filiere filiere = filiereDAO.findById(eleve.getFiliere().getId());
            if (filiere == null) {
                throw new Exception("La filière spécifiée n'existe pas");
            }
            eleve.setFiliere(filiere);
        }

        return eleveDAO.update(eleve);
    }

    /**
     * Supprimer un élève
     */
    public boolean deleteEleve(Long id) throws Exception {
        Eleve eleve = eleveDAO.findById(id);
        if (eleve == null) {
            throw new Exception("Élève non trouvé");
        }

        return eleveDAO.delete(id);
    }

    /**
     * Trouver un élève par ID
     */
    public Eleve findById(Long id) {
        return eleveDAO.findById(id);
    }

    /**
     * Trouver un élève par matricule
     */
    public Eleve findByMatricule(String matricule) {
        return eleveDAO.findByMatricule(matricule);
    }

    /**
     * Trouver les élèves d'une filière
     */
    public List<Eleve> findByFiliere(Long filiereId) {
        return eleveDAO.findByFiliere(filiereId);
    }

    /**
     * Récupérer tous les élèves
     */
    public List<Eleve> findAll() {
        return eleveDAO.findAll();
    }

    /**
     * Compter le nombre d'élèves
     */
    public long count() {
        return eleveDAO.count();
    }

    /**
     * Valider les données d'un élève
     */
    private void validateEleve(Eleve eleve) throws Exception {
        if (eleve == null) {
            throw new Exception("L'élève ne peut pas être null");
        }

        if (eleve.getMatricule() == null || eleve.getMatricule().trim().isEmpty()) {
            throw new Exception("Le matricule est obligatoire");
        }

        if (eleve.getNom() == null || eleve.getNom().trim().isEmpty()) {
            throw new Exception("Le nom est obligatoire");
        }

        if (eleve.getPrenom() == null || eleve.getPrenom().trim().isEmpty()) {
            throw new Exception("Le prénom est obligatoire");
        }

        if (eleve.getEmail() == null || eleve.getEmail().trim().isEmpty()) {
            throw new Exception("L'email est obligatoire");
        }

        if (!EMAIL_PATTERN.matcher(eleve.getEmail()).matches()) {
            throw new Exception("Format d'email invalide");
        }

        if (eleve.getFiliere() == null) {
            throw new Exception("La filière est obligatoire");
        }

        if (eleve.getMatricule().length() > 20) {
            throw new Exception("Le matricule ne peut pas dépasser 20 caractères");
        }

        if (eleve.getNom().length() > 50) {
            throw new Exception("Le nom ne peut pas dépasser 50 caractères");
        }

        if (eleve.getPrenom().length() > 50) {
            throw new Exception("Le prénom ne peut pas dépasser 50 caractères");
        }
    }
}
