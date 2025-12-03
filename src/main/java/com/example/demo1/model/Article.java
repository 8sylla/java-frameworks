package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Article {
    @Id
    private String code;
    private String designation;
    private double prix;

    public Article() {
    }

    public Article(String code, String designation, double prix) {
        this.code = code;
        this.designation = designation;
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Article{" +
                "code='" + code + '\'' +
                ", designation='" + designation + '\'' +
                ", prix=" + prix +
                '}';
    }
}