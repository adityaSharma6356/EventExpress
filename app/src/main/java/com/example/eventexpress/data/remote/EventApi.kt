package com.example.eventexpress.data.remote

import com.example.eventexpress.domain.models.EventModel
import com.example.eventexpress.util.Resource
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException

class EventApi {
    private val fireStore = FirebaseFirestore.getInstance()
    private val eventCollection = fireStore.collection("Events")

    suspend fun upLoadEventData(event: EventModel): Boolean{
        var result = false
        eventCollection.add(event)
            .addOnSuccessListener {
                result = true
                eventCollection.document(it.id).update("id" , it.id)
            }
            .addOnFailureListener {
                result = false
            }
        return result
    }
    suspend fun getEventsData(): Resource<List<EventModel>>{
        return try {
            val data = eventCollection.get().await().toObjects(EventModel::class.java)
            Resource.Success(data, "remote")
        } catch (e: IOException){
            Resource.Error(e.message.toString())
        } catch (e: HttpException){
            Resource.Error(e.message.toString())
        } catch (e: FirebaseTooManyRequestsException){
            Resource.Error(e.message.toString())
        }
    }
}