package com.example.eventexpress.data.local.event

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class EventEntity (
    @PrimaryKey val key : Int? = null,
    val id: String ,
    val name: String ,
    val description: String ,
    val date: String ,
    val timeHour: String ,
    val timeMinute: String ,
    val location: String ,
    val venue: String ,
    val organizers: String ,
    val participantsIds: String ,
    val coverImage: String ,
    val categories: String ,
    val registrationDeadlines: String ,
    val additionalDetails: String ,
    val uploadedByUserid: String ,
)