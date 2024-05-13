package com.navarro.spotifygold.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class AudioEntity(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    @Contextual val uploadAt: Date,
    val duration: Long,
    val author: AuthorEntity
)
