package com.syllahib.hibernategestionacademique.dao;

import com.syllahib.hibernategestionacademique.model.DossierAdministratif;
import com.syllahib.hibernategestionacademique.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * DAO spécifique pour l'entité DossierAdministratif
 */
public class DossierAdministratifDAO extends GenericDAO<DossierAdministratif, Long> {

    public DossierAdministratifDAO() {
        super(DossierAdministratif.class);
    }

    /**
     * Trouver un dossier par l'ID de l'élève
     */
    public DossierAdministratif findByEleveId(Long eleveId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DossierAdministratif> query = em.createQuery(
                    "SELECT d FROM DossierAdministratif d WHERE d.eleve.id = :eleveId",
                    DossierAdministratif.class);
            query.setParameter("eleveId", eleveId);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Trouver un dossier par numéro d'inscription
     */
    public DossierAdministratif findByNumeroInscription(String numeroInscription) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<DossierAdministratif> query = em.createQuery(
                    "SELECT d FROM DossierAdministratif d WHERE d.numeroInscription = :numero",
                    DossierAdministratif.class);
            query.setParameter("numero", numeroInscription);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    /**
     * Vérifier si un élève a déjà un dossier administratif
     */
    public boolean eleveHasDossier(Long eleveId) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(d) FROM DossierAdministratif d WHERE d.eleve.id = :eleveId",
                    Long.class);
            query.setParameter("eleveId", eleveId);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}
