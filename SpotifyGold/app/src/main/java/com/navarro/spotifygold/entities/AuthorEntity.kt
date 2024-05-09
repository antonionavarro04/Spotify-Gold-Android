package com.navarro.spotifygold.entities

data class AuthorEntity(
    val id: String,
    val name: String,
    val url: String = "https://www.youtube.com/channel/$id"
)
