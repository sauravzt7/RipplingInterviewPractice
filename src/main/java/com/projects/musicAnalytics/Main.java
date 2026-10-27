package com.projects.musicAnalytics;

import com.projects.musicAnalytics.models.Song;
import com.projects.musicAnalytics.models.User;
import com.projects.musicAnalytics.repositories.ISongRepo;
import com.projects.musicAnalytics.repositories.IUserRepo;
import com.projects.musicAnalytics.repositories.InMemSongRepo;
import com.projects.musicAnalytics.repositories.InMemUserRepo;
import com.projects.musicAnalytics.services.MusicAnalyticsService;

public class Main {
    public static void main(String[] args) {

        IUserRepo userRepository = new InMemUserRepo();
        ISongRepo songRepository = new InMemSongRepo();
        userRepository.save(new User("u1", "Asha"));
        userRepository.save(new User("u2", "Ravi"));
        userRepository.save(new User("u3", "Meena"));

        MusicAnalyticsService service = new MusicAnalyticsService(userRepository, songRepository);

        service.addSong(new Song("s1", "Song One", "Artist A"));
        service.addSong(new Song("s2", "Song Two", "Artist B"));
        service.addSong(new Song("s3", "Song Three", "Artist C"));
        service.addSong(new Song("s4", "Song Four", "Artist D"));
        service.addSong(new Song("s5", "Song Five", "Artist E"));

        // s2: 2 unique users, 3 plays (u1 plays twice)
        service.playSong("u1", "s2");
        service.playSong("u1", "s2");
        service.playSong("u2", "s2");

        // s1: 2 unique users, 2 plays -> ties s2 on unique users, loses on play count
        service.playSong("u1", "s1");
        service.playSong("u3", "s1");

        // s4 and s3: 1 unique user, 1 play each -> full tie, songId asc puts s3 first
        service.playSong("u2", "s4");
        service.playSong("u3", "s3");

        // s5: never played -> last
        // Expected order: s2, s1, s3, s4, s5

        System.out.println("Analytics:");
        service.getAnalytics().forEach(System.out::println);

        System.out.println("Top 2:");
        service.getTopSongs(2).forEach(System.out::println);

        // One more listener for s3 moves it above s4: 2 users, 2 plays ties s1 fully, songId s1 < s3 keeps s1 ahead
        // Expected order: s2, s1, s3, s4, s5
        service.playSong("u1", "s3");
        System.out.println("After u1 plays s3:");
        service.getAnalytics().forEach(System.out::println);
        service.getTopSongs(2).forEach(System.out::println);

        service.printRecentlyPlayed("u1", 3);
    }
}
