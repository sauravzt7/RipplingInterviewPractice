package com.projects.musicAnalytics.models;

import java.util.Objects;

/**
 * Immutable snapshot of a song and its counters, used as a TreeMap key.
 * A play never mutates a Song; it creates a new one via {@link #played(boolean)},
 * so a key is never changed while it sits inside the TreeMap.
 *
 * Order: uniqueUserCount desc, then playCount desc, then songId asc.
 * equals/hashCode use the same three fields, so compareTo == 0 exactly when equals is true.
 */
public final class Song implements Comparable<Song> {

    private final String songId;
    private final String title;
    private final String artist;
    private final int playCount;
    private final int uniqueUserCount;

    public Song(String songId, String title, String artist) {
        this(songId, title, artist, 0, 0);
    }

    private Song(String songId, String title, String artist, int playCount, int uniqueUserCount) {
        this.songId = Objects.requireNonNull(songId);
        this.title = title;
        this.artist = artist;
        this.playCount = playCount;
        this.uniqueUserCount = uniqueUserCount;
    }

    // O(1): returns the next snapshot after one more play
    public Song played(boolean newListener) {
        return new Song(songId, title, artist, playCount + 1, uniqueUserCount + (newListener ? 1 : 0));
    }

    public String getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getPlayCount() {
        return playCount;
    }

    public int getUniqueUserCount() {
        return uniqueUserCount;
    }

    @Override
    public int compareTo(Song other) {
        int byUsers = Integer.compare(other.uniqueUserCount, this.uniqueUserCount); // desc
        if (byUsers != 0) return byUsers;
        int byPlays = Integer.compare(other.playCount, this.playCount); // desc
        if (byPlays != 0) return byPlays;
        return this.songId.compareTo(other.songId); // asc
    }

    // title/artist are left out: songId is unique, so they add nothing, and leaving them out keeps equals consistent with compareTo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song other)) return false;
        return uniqueUserCount == other.uniqueUserCount
                && playCount == other.playCount
                && songId.equals(other.songId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, uniqueUserCount, playCount);
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId='" + songId + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", uniqueUsers=" + uniqueUserCount +
                ", plays=" + playCount +
                '}';
    }
}
