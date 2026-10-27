package com.projects.articleManagement.repositories;

import com.projects.articleManagement.models.Article;

import java.util.*;



public class InMemArticleRepo implements IArticleRepo {

    private final Map<String, Article> articleMap = new HashMap<>();
    Comparator<Pair> pairComparator = Comparator.comparingInt(Pair::second).thenComparing(Pair::first); // Comparator to sort by score, then by articleId
    TreeSet<Pair> sortedArticles = new TreeSet<>(pairComparator);


    @Override
    public Article save(Article entity) {
        if (articleMap.putIfAbsent(entity.articleId, entity) == null) {
            sortedArticles.add(new Pair(entity.articleId, entity.getScore()));
        }
        return entity;
    }

    @Override
    public Article findById(String id) {
        return articleMap.getOrDefault(id, null);

    }

    @Override
    public void deleteById(String id) {
        articleMap.remove(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return null;
    }

    @Override
    public List<Pair> getArticlesSortedByScore() {
        return sortedArticles.stream().toList();
    }

    @Override
    public void updateArticleScore(Article article, int oldScore) {
        sortedArticles.remove(new Pair(article.articleId, oldScore));
        sortedArticles.add(new Pair(article.articleId, article.getScore()));
    }
}
