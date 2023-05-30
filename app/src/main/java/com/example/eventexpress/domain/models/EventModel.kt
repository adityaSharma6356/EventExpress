package com.example.eventexpress.domain.models

import com.google.firebase.Timestamp


data class EventModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var location: String = "",
    var venue: String = "",
    var organizers: List<String> = listOf(),
    var participantsIds: List<String> = listOf(),
    var coverImage: String = "",
    var categories: List<String> = listOf(),
    var registrationDeadlines: Timestamp = Timestamp(0L, 0),
    var additionalDetails: String = "",
    var uploadedByUserid: String = "",
    var time: Timestamp = Timestamp(0L, 0)
)