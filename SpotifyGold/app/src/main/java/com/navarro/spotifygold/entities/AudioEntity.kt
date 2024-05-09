package com.navarro.spotifygold.entities

import java.util.Date

data class AudioEntity(
    val id: String,
    val title: String,
    val description: String,
    val url: String = "https://www.youtube.com/watch?v=$id",
    val uploadAt: Date,
    val duration: Long,
    val author: AuthorEntity
)
