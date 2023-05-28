package com.example.eventexpress.domain.models

data class UserDataModel (
    var id: String = "",
    var name: String = "",
    var profilePic: String = "",
    var UserEventsIds: MutableList<String> = mutableListOf(),
    var userFavourites: MutableList<String> = mutableListOf(),
)