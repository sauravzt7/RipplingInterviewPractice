package com.projects.articleManagement.repositories;

import com.projects.articleManagement.models.Article;

import java.util.List;

public interface IArticleRepo extends IRepo<Article, String> {
    record Pair(String first, int second) {}

    void updateArticleScore(Article article, int oldScore);
    List<Pair> getArticlesSortedByScore();
}
