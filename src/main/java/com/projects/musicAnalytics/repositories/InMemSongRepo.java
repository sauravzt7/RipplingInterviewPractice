package com.projects.musicAnalytics.repositories;

import com.projects.musicAnalytics.models.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class InMemSongRepo implements ISongRepo {

    private final Map<String, Song> songMap = new HashMap<>(); // songId -> current Song snapshot (the key it has in ranking)
    private final Map<Song, Set<String>> ranking = new TreeMap<>(); // Song (ordered by compareTo) -> userIds who played it

    // O(log N)
    @Override
    public Song save(Song entity) {
        Song old = songMap.put(entity.getSongId(), entity);
        Set<String> listeners = old == null ? new HashSet<>() : ranking.remove(old);
        ranking.put(entity, listeners);
        return entity;
    }

    // O(1)
    @Override
    public Song findById(String id) {
        return songMap.get(id);
    }

    // O(log N): HashMap gives the old key in O(1), so remove + put on the TreeMap are the only log N steps
    @Override
    public Song recordPlay(String songId, String userId) {
        Song song = songMap.get(songId);
        if (song == null) {
            throw new IllegalArgumentException("Song with id " + songId + " does not exist.");
        }
        Set<String> listeners = ranking.remove(song); // exact key, O(log N), no scan
        boolean newListener = listeners.add(userId); // O(1) average
        Song updated = song.played(newListener);
        ranking.put(updated, listeners);
        songMap.put(songId, updated);
        return updated;
        
    }

    // O(N)
    @Override
    public List<Song> getAnalytics() {
        return new ArrayList<>(ranking.keySet());
    }

    // O(log N + K)
    @Override
    public List<Song> getTopSongs(int k) {
        List<Song> top = new ArrayList<>(Math.min(k, ranking.size()));
        for (Song song : ranking.keySet()) {
            if (top.size() == k) break;
            top.add(song);
        }
        return top;
    }
}
