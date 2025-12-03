package com.example.demo1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoArticle {
    private static DaoArticle instance;
    private final List<Article> articles;

    private DaoArticle() {
        articles = new ArrayList<>();
        articles.add(new Article("A001", "Ordinateur portable", 899.99));
        articles.add(new Article("A002", "Souris sans fil", 25.50));
        articles.add(new Article("A003", "Clavier mécanique", 75.00));
    }

    public static synchronized DaoArticle getInstance() {
        if (instance == null) {
            instance = new DaoArticle();
            System.out.println("C'est fait");
        }
        System.out.println("C'est fait");
        return instance;
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    public Optional<Article> findByCode(String code) {
        return articles.stream()
                .filter(a -> a.getCode().equals(code))
                .findFirst();
    }

    public boolean create(Article article) {
        if (findByCode(article.getCode()).isPresent()) {
            return false;
        }
        articles.add(article);
        return true;
    }

    public boolean update(Article article) {
        Optional<Article> existingArticle = findByCode(article.getCode());
        if (existingArticle.isPresent()) {
            Article a = existingArticle.get();
            a.setDesignation(article.getDesignation());
            a.setPrix(article.getPrix());
            return true;
        }
        return false;
    }

    public boolean delete(String code) {
        return articles.removeIf(a -> a.getCode().equals(code));
    }

    public int count() {
        return articles.size();
    }
}