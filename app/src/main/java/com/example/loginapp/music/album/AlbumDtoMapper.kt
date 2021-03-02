package com.example.loginapp.music.album

import com.example.loginapp.music.album.domain.Album
import com.example.loginapp.music.album.dto.AlbumDto
import com.example.loginapp.music.song.SongDtoMapper

object AlbumDtoMapper {

    fun mapDtoDomain(albumDto: AlbumDto): Album {
        return with(albumDto) {
            Album(
                id = id,
                name = name,
                releaseDate = releaseDate,
                songs = songs.map { song -> SongDtoMapper.mapDtoDomain(song) }
            )
        }
    }

    fun mapDomainDto(album: Album): AlbumDto {
        return with(album) {
            AlbumDto(
                id = id,
                name = name,
                releaseDate = releaseDate,
                songs = songs.map { song -> SongDtoMapper.mapDomainDto(song) }
            )
        }
    }
}