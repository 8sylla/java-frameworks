package com.syllahib.hibernategestionacademique.dao;

import com.syllahib.hibernategestionacademique.model.Filiere;
import com.syllahib.hibernategestionacademique.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * DAO spécifique pour l'entité Filiere
 */
public class FiliereDAO extends GenericDAO<Filiere, Long> {

    public FiliereDAO() {
        super(Filiere.class);
    }

    /**
     * Rechercher une filière par son code
     */
    public Filiere findByCode(String code) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Filiere> query = em.createQuery(
                    "SELECT f FROM Filiere f WHERE f.code = :code", Filiere.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Filière non trouvée avec le code: " + code);
            return null;
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
                    "SELECT COUNT(f) FROM Filiere f WHERE f.code = :code", Long.class);
            query.setParameter("code", code);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un code existe pour une autre filière (utile pour update)
     */
    public boolean codeExistsForOther(String code, Long id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(f) FROM Filiere f WHERE f.code = :code AND f.id != :id", Long.class);
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