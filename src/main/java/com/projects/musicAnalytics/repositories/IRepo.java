package com.projects.musicAnalytics.repositories;

public interface IRepo<T, ID> {
    T save(T entity);
    T findById(ID id);
}
