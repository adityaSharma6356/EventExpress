package com.example.eventexpress.domain.models

data class UserDataModel (
    var id: String = "",
    var name: String = "",
    var profilePic: String = "",
    var UserEventsIds: List<String> = listOf(),
    var userFavourites: List<String> = listOf(),
)