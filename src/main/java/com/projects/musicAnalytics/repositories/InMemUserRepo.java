package com.projects.musicAnalytics.repositories;

import com.projects.musicAnalytics.models.User;
import com.projects.musicAnalytics.models.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class InMemUserRepo implements IUserRepo {

    private final Map<String, User> userMap = new HashMap<>(); // userId -> User
    private final Map<String, Set<String>> userSongMap = new HashMap<>(); // userId -> List of songIds

    @Override
    public User save(User entity) {

        userMap.put(entity.getUserId(), entity);
        return entity;
    }

    @Override
    public User findById(String id) {
        return userMap.get(id);
    }

    @Override
    public List<String> recentlyPlayed(String userId) {
        if(!userSongMap.containsKey(userId)) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist.");
        }
        while(userSongMap.get(userId).size() > 100){
            userSongMap.get(userId).remove(userSongMap.get(userId).iterator().next()); // remove the oldest song if more than 100 songs are played
        }
        return userSongMap.get(userId).stream().toList();
    }

    public List<String> recentlyPlayed(String userId, int k) {
        if(!userSongMap.containsKey(userId)) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist.");
        }
        if(k < 0) {
            throw new IllegalArgumentException("k must be non-negative.");
        }
        if(k > 100) {
            throw new IllegalArgumentException("k must be less than or equal to 100.");
        }
        return userSongMap.get(userId).stream().limit(k).toList();
    }

    @Override
    public void playSong(String userId, String songId) {
        userSongMap.computeIfAbsent(userId, k -> new java.util.HashSet<>()).add(songId);
    }


}
