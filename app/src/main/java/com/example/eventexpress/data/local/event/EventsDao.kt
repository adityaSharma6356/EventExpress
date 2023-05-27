package com.example.eventexpress.data.local.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(
        eventsEntities: List<EventEntity>
    )

    @Query("DELETE FROM evententity")
    suspend fun clearEvents()

    @Query(
        """
            SELECT *
            FROM evententity
            WHERE LOWER(id) == LOWER(:query) 
        """
    )
    suspend fun searchEvents(query: String): List<EventEntity>

    @Query(
        """
            SELECT *
            FROM evententity
        """
    )
    suspend fun getAllEvents() : List<EventEntity>
}