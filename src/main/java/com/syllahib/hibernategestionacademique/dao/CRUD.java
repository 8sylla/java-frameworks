package com.syllahib.hibernategestionacademique.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface générique CRUD pour les opérations de base
 * @param <T> Type de l'entité
 * @param <PK> Type de la clé primaire (Primary Key)
 */
public interface CRUD<T, PK extends Serializable> {

    /**
     * Créer une nouvelle entité
     * @param entity l'entité à créer
     * @return l'entité créée avec son ID
     */
    T create(T entity);

    /**
     * Mettre à jour une entité existante
     * @param entity l'entité à mettre à jour
     * @return l'entité mise à jour
     */
    T update(T entity);

    /**
     * Supprimer une entité par son ID
     * @param id l'identifiant de l'entité à supprimer
     * @return true si supprimé avec succès, false sinon
     */
    boolean delete(PK id);

    /**
     * Rechercher une entité par son ID
     * @param id l'identifiant de l'entité
     * @return l'entité trouvée ou null
     */
    T findById(PK id);

    /**
     * Récupérer toutes les entités
     * @return liste de toutes les entités
     */
    List<T> findAll();

    /**
     * Compter le nombre total d'entités
     * @return le nombre d'entités
     */
    long count();
}
