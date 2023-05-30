package com.example.eventexpress.data.local.event

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eventexpress.data.mappers.Converters


@Database(
    entities = [EventEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class EventsDatabase : RoomDatabase() {
    abstract val eventsDao : EventsDao
}