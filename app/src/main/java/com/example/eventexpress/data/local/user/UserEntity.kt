package com.example.eventexpress.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity (
    @PrimaryKey val key : Int? = null,
    val id: String ,
    val name: String ,
    val profilePic: String ,
    val UserEventsIds: String ,
    val userFavourites: String ,
)