package com.syllahib.hibernategestionacademique.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitaire pour gérer EntityManagerFactory et EntityManager
 * Pattern Singleton pour l'EntityManagerFactory
 */
public class HibernateUtil {

    private static final String PERSISTENCE_UNIT_NAME = "gestion-academique-pu";
    private static EntityManagerFactory entityManagerFactory;

    // Constructeur privé pour empêcher l'instanciation
    private HibernateUtil() {
    }

    /**
     * Obtenir l'EntityManagerFactory (Singleton)
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            synchronized (HibernateUtil.class) {
                if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
                    try {
                        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                        System.out.println("EntityManagerFactory créé avec succès");
                    } catch (Exception e) {
                        System.err.println("Erreur lors de la création de l'EntityManagerFactory: " + e.getMessage());
                        e.printStackTrace();
                        throw new ExceptionInInitializerError(e);
                    }
                }
            }
        }
        return entityManagerFactory;
    }

    /**
     * Créer un nouvel EntityManager
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Fermer l'EntityManagerFactory
     * À appeler lors de l'arrêt de l'application
     */
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("EntityManagerFactory fermé");
        }
    }

    /**
     * Fermer un EntityManager de manière sécurisée
     */
    public static void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            try {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de l'EntityManager: " + e.getMessage());
            }
        }
    }
}
