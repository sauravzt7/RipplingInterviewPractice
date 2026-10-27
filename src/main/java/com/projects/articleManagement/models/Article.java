package com.projects.articleManagement.models;

public class Article {

    public String articleId;
    public String title;
    public String content;
    public Integer upVotes;
    public Integer downVotes;
    public Integer score;



    public Article(String articleId, String title, String content, Integer upVotes, Integer downVotes) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public int getScore() {
        return this.score = this.upVotes - this.downVotes;
    }


    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", upVotes=" + upVotes +
                ", downVotes=" + downVotes +
                ", score=" + score +
                '}';
    }
}
