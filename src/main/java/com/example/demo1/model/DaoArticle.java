package com.example.demo1.model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class DaoArticle implements IDao<Article> {
    Transaction Tx=null;
    private List<Article> articles = new CopyOnWriteArrayList<>();
    private static DaoArticle instance = new DaoArticle();

    // private DaoArticle() {
    //     //articles = findAll();
    //     // articles.add(new Article("Art1","Article1",120));
    //     // articles.add(new Article("Art2","Article2",150));
    //     // articles.add(new Article("Art3","Article3",180));
    // }
    // Singleton simple pour partager la même instance dans toute l'application
    public static DaoArticle getInstance() {
        return instance;
    }

    public List<Article> findAll() {
        // return new ArrayList<>(articles);
        List<Article> articles = new ArrayList<Article>();

        try {
            Session S= HibernateUtil.getSessionFactory().openSession();
            Tx =S.beginTransaction();
            System.err.println("LISTING ARTICLES SUCCESS");
            String Req ="FROM Article";
            articles = S.createQuery(Req).list();
            Tx.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return articles;
    }

    public Article findByCode(String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Article article = session.get(Article.class, code);
        session.close();
        return article;
    }

    public boolean create(Article article) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction  tx = session.beginTransaction();

        Article art = session.get(Article.class, article.getCode());

        if (art != null) {
            session.close();
            return false;
        }
        session.save(article);
        tx.commit();
        session.close();
        return true;
    }

    public boolean update(Article article) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(article);
        tx.commit();
        session.close();
        return true;
    }

    public boolean delete(String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Article article = session.get(Article.class, code);
        session.delete(article);
        tx.commit();
        session.close();
        return true;
    }
}