package com.navarro.spotifygold.entities

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class DtoResultEntity(
    val id: String,
    val title: String,
    val authorName: String,
    val thumbnail: String
)
