package com.projects.articleManagement;

import com.projects.articleManagement.models.Article;
import com.projects.articleManagement.models.User;
import com.projects.articleManagement.repositories.IArticleRepo;
import com.projects.articleManagement.repositories.IUserRepo;
import com.projects.articleManagement.repositories.InMemArticleRepo;
import com.projects.articleManagement.repositories.InMemUserRepo;
import com.projects.articleManagement.services.ArticleManagerService;

public class Main {
    public static void main(String[] args) {

        Article article1 = new Article("1", "Title 1", "Content 1", 10, 2);
        Article article2 = new Article("2", "Title 2", "Content 2", 5, 1);
        Article article3 = new Article("3", "Title 3", "Content 3", 8, 3);
        Article article4 = new Article("4", "Title 4", "Content 4", 12, 4);


        User user1 = new User("1", "Reja");
        User user2 = new User("2", "keja");


        IArticleRepo articleRepository = new InMemArticleRepo();
        IUserRepo userRepository = new InMemUserRepo();
        userRepository.save(user1);
        userRepository.save(user2);


        ArticleManagerService articleManagerService = new ArticleManagerService(articleRepository,userRepository);

        articleManagerService.addArticle(article1);
        articleManagerService.addArticle(article2);
        articleManagerService.addArticle(article3);
        articleManagerService.addArticle(article4);


        articleManagerService.upVote("1", "1");
        articleManagerService.upVote("2", "1");
        articleManagerService.upVote("3", "2");

        articleManagerService.downVote("1", "1");
        articleManagerService.downVote("2", "1");
        articleManagerService.downVote("3", "2");

        System.out.println(articleManagerService.getLastKFlippedArticles( 2));

        System.out.println("Articles sorted by score:");
        articleManagerService.getArticlesByScore().forEach(System.out::println);

        articleManagerService.upVote("4", "1");
        articleManagerService.upVote("4", "2");
        articleManagerService.downVote("4", "1");

        System.out.println("Articles sorted by score after voting on article 4:");

        articleManagerService.getArticlesByScore().forEach(System.out::println);
        System.out.println("User 1 last vote: " + userRepository.getUserLastVote("1"));



    }
}
