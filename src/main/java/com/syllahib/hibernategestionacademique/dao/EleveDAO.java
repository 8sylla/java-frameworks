package com.syllahib.hibernategestionacademique.dao;

import com.syllahib.hibernategestionacademique.model.Eleve;
import com.syllahib.hibernategestionacademique.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO spécifique pour l'entité Eleve
 */
public class EleveDAO extends GenericDAO<Eleve, Long> {

    public EleveDAO() {
        super(Eleve.class);
    }

    /**
     * Rechercher un élève par son matricule
     */
    public Eleve findByMatricule(String matricule) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Eleve> query = em.createQuery(
                    "SELECT e FROM Eleve e WHERE e.matricule = :matricule", Eleve.class);
            query.setParameter("matricule", matricule);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Élève non trouvé avec le matricule: " + matricule);
            return null;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Rechercher les élèves d'une filière
     */
    public List<Eleve> findByFiliere(Long filiereId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Eleve> query = em.createQuery(
                    "SELECT e FROM Eleve e WHERE e.filiere.id = :filiereId ORDER BY e.nom, e.prenom",
                    Eleve.class);
            query.setParameter("filiereId", filiereId);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche des élèves: " + e.getMessage());
            return List.of();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Rechercher un élève par email
     */
    public Eleve findByEmail(String email) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Eleve> query = em.createQuery(
                    "SELECT e FROM Eleve e WHERE e.email = :email", Eleve.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un matricule existe déjà
     */
    public boolean matriculeExists(String matricule) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(e) FROM Eleve e WHERE e.matricule = :matricule", Long.class);
            query.setParameter("matricule", matricule);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un email existe déjà
     */
    public boolean emailExists(String email) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(e) FROM Eleve e WHERE e.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un matricule existe pour un autre élève
     */
    public boolean matriculeExistsForOther(String matricule, Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(e) FROM Eleve e WHERE e.matricule = :matricule AND e.id != :id",
                    Long.class);
            query.setParameter("matricule", matricule);
            query.setParameter("id", id);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un email existe pour un autre élève
     */
    public boolean emailExistsForOther(String email, Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(e) FROM Eleve e WHERE e.email = :email AND e.id != :id",
                    Long.class);
            query.setParameter("email", email);
            query.setParameter("id", id);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}
