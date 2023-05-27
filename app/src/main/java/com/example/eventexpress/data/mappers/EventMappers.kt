package com.example.eventexpress.data.mappers

import com.example.eventexpress.data.local.event.EventEntity
import com.example.eventexpress.domain.models.EventModel


fun EventEntity.toEventModel() : EventModel{

    val orgList: List<String> = listOf(*organizers.split(",").toTypedArray())
    val partiList: List<String> = listOf(*participantsIds.split(",").toTypedArray())
    val cateList: List<String> = listOf(*categories.split(",").toTypedArray())
    return EventModel(
        id,
        name,
        description,
        date,
        timeHour,
        timeMinute,
        location,
        venue,
        orgList,
        partiList,
        coverImage,
        cateList,
        registrationDeadlines,
        additionalDetails,
        uploadedByUserid
    )
}

fun EventModel.toEventEntity() : EventEntity{
    return EventEntity(
        null,
        id,
        name,
        description,
        date,
        timeHour,
        timeMinute,
        location,
        venue,
        organizers.joinToString(","),
        participantsIds.joinToString(","),
        coverImage,
        categories.joinToString(","),
        registrationDeadlines,
        additionalDetails,
        uploadedByUserid
    )
}
