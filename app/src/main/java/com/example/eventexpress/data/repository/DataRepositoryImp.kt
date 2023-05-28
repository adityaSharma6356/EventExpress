package com.example.eventexpress.data.repository

import android.util.Log
import com.example.eventexpress.data.local.event.EventsDatabase
import com.example.eventexpress.data.local.user.UserDatabase
import com.example.eventexpress.data.mappers.toEventEntity
import com.example.eventexpress.data.mappers.toEventModel
import com.example.eventexpress.data.mappers.toUserDataModel
import com.example.eventexpress.data.mappers.toUserEntity
import com.example.eventexpress.data.remote.EventApi
import com.example.eventexpress.data.remote.UsersApi
import com.example.eventexpress.domain.models.EventModel
import com.example.eventexpress.domain.models.UserDataModel
import com.example.eventexpress.domain.repository.DataRepository
import com.example.eventexpress.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepositoryImp @Inject constructor(
    private val eventsApi: EventApi,
    private val usersApi: UsersApi,
    private val eventsDatabase: EventsDatabase,
    private val userDatabase: UserDatabase
): DataRepository {

    private val userDb = userDatabase.userDao
    private val eventDb = eventsDatabase.eventsDao

    override suspend fun getUserData(
        userId: String
    ): Flow<Resource<UserDataModel>> {
        return flow {

            emit(Resource.Loading(isLoading = true))
            Log.d("likestate", userId)
            val localUserData = userDb.searchUserData(userId)
            if(localUserData!=null){
                emit(Resource.Success(localUserData.toUserDataModel(), "local"))
            }
            val remoteUserData = usersApi.getUserDataById(userId)

            if(remoteUserData!=null){
                userDb.insertUserData(remoteUserData.toUserEntity())
                Log.d("likestate", "remote: $remoteUserData")
                emit(Resource.Success(remoteUserData, "remote"))
            } else {
                emit(Resource.Error("noData"))
            }

            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun saveUserData(data: UserDataModel): Boolean {
        val success: Boolean = usersApi.saveUserData(data)
        if(success){
            userDb.insertUserData(data.toUserEntity())
        }
        return success
    }

    override suspend fun getEvents(local: Boolean): Flow<Resource<List<EventModel>>> {
        return flow {
            emit(Resource.Loading(true))

            val localData = eventDb.getAllEvents()

            Resource.Success(localData.map { it.toEventModel() }, "local")
            if(!local){
                when(val remoteData = eventsApi.getEventsData()){
                    is Resource.Success -> {
                        remoteData.data?.let {
                            eventDb.clearEvents()
                            eventDb.insertEvents(
                                it.map { event ->
                                    event.toEventEntity()
                                }) }
                        emit(Resource.Success(eventDb.getAllEvents().map { it.toEventModel() }, "remote"))
                    }
                    is Resource.Error -> {
//                    emit(remoteData)
                    }
                    else -> Unit
                }
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun publishEvent(event: EventModel):Boolean {
        return eventsApi.upLoadEventData(event)
    }

    override suspend fun deleteAllEvents() {
        eventDb.clearEvents()
    }
}











