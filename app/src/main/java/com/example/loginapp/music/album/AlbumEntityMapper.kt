package com.example.loginapp.music.album

import com.example.loginapp.music.album.database.AlbumEntity
import com.example.loginapp.music.album.domain.Album
import com.example.loginapp.music.song.SongEntityMapper
import com.example.loginapp.music.song.database.SongEntity

object AlbumEntityMapper {

    fun mapDomainEntity(album: Album): AlbumEntity {
        return with(album) {
            AlbumEntity(
                id = id,
                name = name,
                releaseDate = releaseDate
            )
        }
    }

    fun mapEntityDomain(album: AlbumEntity, listSongEntity: List<SongEntity>): Album {
        return with(album) {
            Album(
                id = id,
                name = name,
                releaseDate = releaseDate,
                songs = listSongEntity.map { song -> SongEntityMapper.mapEntityDomain(song) }
            )
        }
    }
}