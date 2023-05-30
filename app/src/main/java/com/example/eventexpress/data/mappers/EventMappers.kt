package com.example.eventexpress.data.mappers

import androidx.room.TypeConverter
import com.example.eventexpress.data.local.event.EventEntity
import com.example.eventexpress.domain.models.EventModel
import com.google.firebase.Timestamp


fun EventEntity.toEventModel() : EventModel{

    val orgList: List<String> = listOf(*organizers.split(",").toTypedArray())
    val partiList: List<String> = listOf(*participantsIds.split(",").toTypedArray())
    val cateList: List<String> = listOf(*categories.split(",").toTypedArray())
    return EventModel(
        id,
        name,
        description,
        location,
        venue,
        orgList,
        partiList,
        coverImage,
        cateList,
        registrationDeadlines,
        additionalDetails,
        uploadedByUserid,
        time
    )
}

fun EventModel.toEventEntity() : EventEntity{
    return EventEntity(
        null,
        id,
        name,
        description,
        location,
        venue,
        organizers.joinToString(","),
        participantsIds.joinToString(","),
        coverImage,
        categories.joinToString(","),
        registrationDeadlines,
        additionalDetails,
        uploadedByUserid,
        time
    )
}

class Converters {
    @TypeConverter
    fun longToTimeStamp(long: Long): Timestamp {
        return Timestamp(long, 0)
    }

    @TypeConverter
    fun timestampToLong(time:Timestamp): Long {
        return time.seconds
    }
}

