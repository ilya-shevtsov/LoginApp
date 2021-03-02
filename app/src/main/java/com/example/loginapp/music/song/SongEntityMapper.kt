package com.example.loginapp.music.song

import com.example.loginapp.music.song.database.SongEntity
import com.example.loginapp.music.song.domain.Song

object SongEntityMapper {

    fun mapDomainEntity(song: Song, albumId: Int): SongEntity {
        return with(song) {
            SongEntity(
                id = id,
                name = name,
                duration = duration,
                albumID = albumId
            )
        }
    }

    fun mapEntityDomain(song: SongEntity): Song {
        return with(song) {
            Song(
                id = id,
                name = name,
                duration = duration
            )
        }
    }
}