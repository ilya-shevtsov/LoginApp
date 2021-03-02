package com.example.loginapp.music.song

import com.example.loginapp.music.song.domain.Song
import com.example.loginapp.music.song.dto.SongDto

object SongDtoMapper {

    fun mapDtoDomain(songDto: SongDto): Song {
        return with(songDto) {
            Song(
                id = id,
                name = name,
                duration = duration
            )
        }
    }

    fun mapDomainDto(song: Song): SongDto {
        return with(song) {
            SongDto(
                id = id,
                name = name,
                duration = duration
            )
        }
    }
}
