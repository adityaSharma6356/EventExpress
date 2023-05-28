package com.example.eventexpress.presentation

import com.example.eventexpress.domain.models.EventModel
import com.example.eventexpress.domain.models.UserDataModel

data class UiStates(
    var eventsList : List<EventModel> = emptyList(),
    var tempEventsList : List<EventModel> = emptyList(),
    var userData : UserDataModel = UserDataModel(),
    var loading: Boolean = false,
    var loadingSignIn: Boolean = false,
    var currentEvent: EventModel = EventModel()
)
