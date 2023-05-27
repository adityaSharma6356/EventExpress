package com.example.eventexpress.domain.repository

import com.example.eventexpress.domain.models.EventModel
import com.example.eventexpress.domain.models.UserDataModel
import com.example.eventexpress.presentation.sign_in.GoogleAuthUIClient
import com.example.eventexpress.util.Resource
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun getUserData(
        userId: String
    ) : Flow<Resource<UserDataModel>>

    suspend fun saveUserData(
        data: UserDataModel
    ) : Boolean

    suspend fun getEvents(
        local: Boolean
    ) : Flow<Resource<List<EventModel>>>

    suspend fun publishEvent(
        event: EventModel
    ) : Boolean

    suspend fun deleteAllEvents()
}