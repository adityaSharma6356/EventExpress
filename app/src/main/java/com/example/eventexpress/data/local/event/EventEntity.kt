package com.example.eventexpress.data.local.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp


@Entity
data class EventEntity (
    @PrimaryKey val key : Int? = null,
    val id: String ,
    val name: String ,
    val description: String ,
    val location: String ,
    val venue: String ,
    val organizers: String ,
    val participantsIds: String ,
    val coverImage: String ,
    val categories: String ,
    val registrationDeadlines: Timestamp ,
    val additionalDetails: String ,
    val uploadedByUserid: String ,
    val time: Timestamp
)