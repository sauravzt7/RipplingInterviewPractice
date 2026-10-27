Problem: Music Analytics System



Design a system to track song playback and generate analytics. The system should efficiently support the following operations:

addSong(songId, songName): Adds a new song to the system.
playSong(userId, songId): Records that a specific user has played a given song.
getAnalytics(): 
Returns a list of all songs, sorted in descending order based on the number of unique users who have played each song. If two songs have the same number of unique users, they should then be sorted by their total play count in descending order. If both these metrics are equal, sort by songId in ascending order.
Consider how to efficiently update and retrieve this data, especially under a high volume of song playbacks and a large number of users/songs. Your solution should account for both space and time complexity for these operations.


Most Played Songs by Unique Users
add_song(songId)
play_song(userId, songId)
print_analytics()

Part 2: 
Recently Played Unique Songs
print_recently_played(userId)
print_recently_played(userId, k)