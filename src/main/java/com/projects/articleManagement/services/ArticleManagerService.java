package com.projects.articleManagement.services;

import com.projects.articleManagement.models.Article;
import com.projects.articleManagement.models.User;
import com.projects.articleManagement.repositories.IArticleRepo;
import com.projects.articleManagement.repositories.IUserRepo;

import java.util.LinkedList;
import java.util.List;

public class ArticleManagerService {

    private final IArticleRepo articleRepo;
    private final IUserRepo userRepo;
    private final List<Article> flippedArticles; // List to keep track of flipped articles

    public ArticleManagerService(IArticleRepo articleRepo, IUserRepo userRepo) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
        this.flippedArticles = new LinkedList<>();
    }

    public void addArticle(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }
        articleRepo.save(article);
    }

    public void upVote(String articleId, String userId) {
        vote(articleId, userId, 1);
    }

    public void downVote(String articleId, String userId) {
        vote(articleId, userId, -1);
    }

    // dir: 1 for upvote, -1 for downvote
    private void vote(String articleId, String userId, int dir) {
        Article article = articleRepo.findById(articleId);
        User user = userRepo.findById(userId);

        if (article == null || user == null) {
            throw new IllegalArgumentException("Article or User not found");
        }

        Integer prev = user.interactedArticles.get(article);
        if (prev != null && prev == dir) {
            return; // same vote again, nothing changes
        }

        int oldScore = article.getScore(); // capture before mutating so the sorted set can find the old pair

        if (prev != null) {
            // flip: undo the previous vote
            if (prev == 1) article.upVotes -= 1;
            else article.downVotes -= 1;
            flippedArticles.add(article);
        }

        if (dir == 1) article.upVotes += 1;
        else article.downVotes += 1;

        user.interactedArticles.putLast(article, dir); // move to end so lastEntry() is the latest vote
        articleRepo.updateArticleScore(article, oldScore);
    }

    public List<Article> getLastKFlippedArticles(int k){

        if(k <= 0) {
            throw new IllegalArgumentException("K must be greater than 0");
        }
        if(k > flippedArticles.size()) {
            throw new IllegalArgumentException("K is greater than the number of flipped articles");
        }

        return flippedArticles.subList(flippedArticles.size() - k, flippedArticles.size());
    }

    public List<IArticleRepo.Pair> getArticlesByScore() {
        return articleRepo.getArticlesSortedByScore();
    }
}
