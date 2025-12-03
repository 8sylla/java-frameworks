package com.example.demo1.model;

import java.util.List;

public interface IDao<T> {
    boolean update(T t);
    List<T> findAll();
    T findByCode(String code);
    boolean create(T t);
    boolean delete(String code);
}
