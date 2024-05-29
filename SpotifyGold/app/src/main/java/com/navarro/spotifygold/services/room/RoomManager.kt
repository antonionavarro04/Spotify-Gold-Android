package com.navarro.spotifygold.services.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.navarro.spotifygold.entities.metadata.AuthorEntity
import com.navarro.spotifygold.entities.metadata.EngagementEntity
import com.navarro.spotifygold.entities.metadata.MetadataEntity
import com.navarro.spotifygold.entities.metadata.ThumbnailEntity

@Database(
    entities = [
        MetadataEntity::class,
        EngagementEntity::class,
        AuthorEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun metadataDao(): MetadataDao
}

@Dao
interface MetadataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMetadata(metadata: MetadataEntity)

    @Transaction
    @Query("SELECT * FROM MetadataEntity WHERE id = :id")
    fun getMetadata(id: String): MetadataEntity
}
