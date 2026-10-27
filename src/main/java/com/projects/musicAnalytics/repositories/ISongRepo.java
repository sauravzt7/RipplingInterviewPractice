package com.projects.musicAnalytics.repositories;

import com.projects.musicAnalytics.models.Song;

import java.util.List;

public interface ISongRepo extends IRepo<Song, String> {
    Song recordPlay(String songId, String userId); // updates counters and ranking, returns the new snapshot

    List<Song> getAnalytics(); // returns a list of all songs sorted in descending order based on the number of unique users who have played each song. If two songs have the same number of unique users, they should then be sorted by their total play count in descending order. If both these metrics are equal, sort by songId in ascending order.

    List<Song> getTopSongs(int k); // first k songs of getAnalytics()
}
