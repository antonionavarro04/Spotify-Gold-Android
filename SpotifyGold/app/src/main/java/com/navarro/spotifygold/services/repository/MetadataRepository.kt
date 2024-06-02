package com.navarro.spotifygold.services.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.navarro.spotifygold.entities.metadata.AuthorEntity
import com.navarro.spotifygold.entities.metadata.MetadataEntity

@Dao
interface MetadataRepository {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMetadata(metadata: MetadataEntity)

    @Query("SELECT * FROM MetadataEntity WHERE id = :id")
    fun getMetadata(id: String): MetadataEntity

    @Query("SELECT DISTINCT author_id AS id, author_name AS name FROM MetadataEntity")
    fun getAuthors(): List<AuthorEntity>

    @Query("SELECT id FROM MetadataEntity WHERE author_id = :authorId")
    fun getIdsByAuthorId(authorId: String): List<String>
}