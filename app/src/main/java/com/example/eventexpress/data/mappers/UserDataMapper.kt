package com.example.eventexpress.data.mappers

import com.example.eventexpress.data.local.user.UserEntity
import com.example.eventexpress.domain.models.UserDataModel
import com.example.eventexpress.presentation.sign_in.UserData

fun UserDataModel.toUserEntity(): UserEntity{
    return UserEntity(
        null ,id, name,profilePic, UserEventsIds.toString(), userFavourites.toString()
    )
}
fun UserEntity.toUserDataModel(): UserDataModel{
    val eventId: List<String> = listOf(*UserEventsIds.split(",").toTypedArray())
    val fav: List<String> = listOf(*userFavourites.split(",").toTypedArray())
    return UserDataModel(
        id, name,profilePic, eventId, fav
    )
}
fun UserData.toUserDataModel(): UserDataModel{
    return UserDataModel(
        id = userId,
        name = username ?: "",
        profilePic = profilePictureUrl ?: ""
    )
}