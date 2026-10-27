package com.projects.musicAnalytics.services;

import com.projects.musicAnalytics.repositories.IUserRepo;
import com.projects.musicAnalytics.models.Song;
import com.projects.musicAnalytics.repositories.ISongRepo;

import java.util.List;

public class MusicAnalyticsService {
    IUserRepo userRepo;
    ISongRepo songRepo;

    public MusicAnalyticsService(IUserRepo userRepo, ISongRepo songRepo) {
        this.userRepo = userRepo;
        this.songRepo = songRepo;
    }

    public List<String> recentlyPlayed(String userId) {
        return userRepo.recentlyPlayed(userId);
    }

    public void addSong(Song song) {
        if(songRepo.findById(song.getSongId()) != null) {
            throw new IllegalArgumentException("Song with id " + song.getSongId() + " already exists.");
        }
        songRepo.save(song);
    }

    public void playSong(String userId, String songId) {
        if(songRepo.findById(songId) == null) {
            throw new IllegalArgumentException("Song with id " + songId + " does not exist.");
        }
        songRepo.recordPlay(songId, userId);
        userRepo.playSong(userId, songId);
    }

    public List<Song> getAnalytics() {
        return songRepo.getAnalytics();
    }

    public List<Song> getTopSongs(int k) {
        return songRepo.getTopSongs(k);
    }


    public void printRecentlyPlayed(String u1, int i) {
        List<String> recentlyPlayed = userRepo.recentlyPlayed(u1, i);
        System.out.println("Recently played songs for user " + u1 + ":");
        for (String songId : recentlyPlayed) {
            System.out.println(songId);
        }
    }
}
