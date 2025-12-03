package com.syllahib.hibernategestionacademique.dao;

import com.syllahib.hibernategestionacademique.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Implémentation générique du DAO
 * @param <T> Type de l'entité
 * @param <PK> Type de la clé primaire
 */
public abstract class GenericDAO<T, PK extends Serializable> implements CRUD<T, PK> {

    protected Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T create(T entity) {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = HibernateUtil.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            em.persist(entity);

            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de la création: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de l'entité", e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    @Override
    public T update(T entity) {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = HibernateUtil.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            T merged = em.merge(entity);

            transaction.commit();
            return merged;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de la mise à jour: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'entité", e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    @Override
    public boolean delete(PK id) {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = HibernateUtil.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                transaction.commit();
                return true;
            }

            transaction.commit();
            return false;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de l'entité", e);
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    @Override
    public T findById(PK id) {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            return em.find(entityClass, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);

            TypedQuery<T> query = em.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de toutes les entités: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }

    @Override
    public long count() {
        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> root = cq.from(entityClass);
            cq.select(cb.count(root));

            return em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            System.err.println("Erreur lors du comptage: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            HibernateUtil.closeEntityManager(em);
        }
    }
}