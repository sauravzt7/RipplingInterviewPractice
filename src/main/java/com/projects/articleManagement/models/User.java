package com.projects.articleManagement.models;


import java.util.LinkedHashMap;

public class User {

    public String userId;
    String name;
    public LinkedHashMap<Article, Integer> interactedArticles; // List of articles liked by the user 1 for liked, -1 for disliked

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        interactedArticles = new LinkedHashMap<>();
    }


}
