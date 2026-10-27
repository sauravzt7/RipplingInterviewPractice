package com.projects.articleManagement.repositories;

import java.util.List;

public interface IRepo<T, ID> {
    T save(T entity);
    T findById(ID id);
    void deleteById(ID id);
    Iterable<T> findAll();


}
