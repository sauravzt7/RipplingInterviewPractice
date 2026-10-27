package com.projects.articleManagement.repositories;

import com.projects.articleManagement.models.Article;
import com.projects.articleManagement.models.User;

public interface IUserRepo extends IRepo<User, String> {
    Article getUserLastVote(String userId);
}
