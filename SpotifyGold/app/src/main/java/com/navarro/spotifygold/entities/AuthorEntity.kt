package com.navarro.spotifygold.entities

import kotlinx.serialization.Serializable

@Serializable
data class AuthorEntity(
    val id: String,
    val name: String,
    val url: String = "https://www.youtube.com/channel/$id"
)
