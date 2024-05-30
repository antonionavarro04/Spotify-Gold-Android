package com.navarro.spotifygold.services.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.navarro.spotifygold.entities.metadata.MetadataEntity

@Dao
interface MetadataRepository {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMetadata(metadata: MetadataEntity)

    @Transaction
    @Query("SELECT * FROM MetadataEntity WHERE id = :id")
    fun getMetadata(id: String): MetadataEntity
}