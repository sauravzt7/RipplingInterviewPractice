package com.projects.articleManagement.repositories;

import com.projects.articleManagement.models.Article;
import com.projects.articleManagement.models.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InMemUserRepo implements IUserRepo {

    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public User save(User entity) {
        userMap.putIfAbsent(entity.userId, entity);
        return entity;
    }

    @Override
    public User findById(String id) {
        return userMap.getOrDefault(id, null);
    }

    @Override
    public void deleteById(String id) {
        userMap.remove(id);
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Article getUserLastVote(String userId) {
        User user = userMap.get(userId);
        if (user == null) throw new IllegalArgumentException("User not found");
        var last = user.interactedArticles.lastEntry();   // O(1), Java 21
        return last == null ? null : last.getKey();
    }

}
