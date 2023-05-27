package com.example.eventexpress.domain.models

data class EventModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var date: String = "",
    var timeHour: String = "",
    var timeMinute: String = "",
    var location: String = "",
    var venue: String = "",
    var organizers: List<String> = listOf(),
    var participantsIds: List<String> = listOf(),
    var coverImage: String = "",
    var categories: List<String> = listOf(),
    var registrationDeadlines: String = "",
    var additionalDetails: String = "",
    var uploadedByUserid: String = "",
)