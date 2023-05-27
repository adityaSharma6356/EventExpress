package com.example.eventexpress.data.local.event

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [EventEntity::class],
    version = 1
)
abstract class EventsDatabase : RoomDatabase() {
    abstract val eventsDao : EventsDao
}