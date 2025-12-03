package com.syllahib.hibernategestionacademique.dao;

import com.syllahib.hibernategestionacademique.model.Cours;
import com.syllahib.hibernategestionacademique.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO spécifique pour l'entité Cours
 */
public class CoursDAO extends GenericDAO<Cours, Long> {

    public CoursDAO() {
        super(Cours.class);
    }

    /**
     * Rechercher un cours par son code
     */
    public Cours findByCode(String code) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Cours> query = em.createQuery(
                    "SELECT c FROM Cours c WHERE c.code = :code", Cours.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Cours non trouvé avec le code: " + code);
            return null;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Rechercher les cours d'une filière
     */
    public List<Cours> findByFiliere(Long filiereId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Cours> query = em.createQuery(
                    "SELECT c FROM Cours c JOIN c.filieres f WHERE f.id = :filiereId ORDER BY c.code",
                    Cours.class);
            query.setParameter("filiereId", filiereId);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche des cours: " + e.getMessage());
            return List.of();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Rechercher les cours d'un élève
     */
    public List<Cours> findByEleve(Long eleveId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Cours> query = em.createQuery(
                    "SELECT c FROM Cours c JOIN c.eleves e WHERE e.id = :eleveId ORDER BY c.code",
                    Cours.class);
            query.setParameter("eleveId", eleveId);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche des cours: " + e.getMessage());
            return List.of();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un code existe déjà
     */
    public boolean codeExists(String code) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(c) FROM Cours c WHERE c.code = :code", Long.class);
            query.setParameter("code", code);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un code existe pour un autre cours
     */
    public boolean codeExistsForOther(String code, Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(c) FROM Cours c WHERE c.code = :code AND c.id != :id",
                    Long.class);
            query.setParameter("code", code);
            query.setParameter("id", id);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}
