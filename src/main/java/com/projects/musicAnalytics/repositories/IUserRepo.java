package com.projects.musicAnalytics.repositories;

import com.projects.musicAnalytics.models.User;


import java.util.List;

public interface IUserRepo extends IRepo<User, String> {

    List<String> recentlyPlayed(String userId);
    List<String> recentlyPlayed(String userId, int k);

    void playSong(String userId, String songId);
}
