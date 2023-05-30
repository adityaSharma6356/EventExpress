package com.example.eventexpress.data.remote

import com.example.eventexpress.domain.models.UserDataModel
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException

class UsersApi {
    private val fireStore = FirebaseFirestore.getInstance()
    private val userCollection = fireStore.collection("usersData")

    suspend fun getUserDataById(userId: String): UserDataModel? {
        return try {
            userCollection.document(userId).get().await().toObject(UserDataModel::class.java)
        } catch (e: IOException){
            return null
        } catch (e: HttpException){
            return null
        } catch (e: FirebaseTooManyRequestsException){
            return null
        }
    }
    suspend fun saveUserData(data: UserDataModel): Boolean {
        var result = false
        userCollection
            .document(data.id)
            .set(data)
            .addOnSuccessListener {
                result = true
            }
            .addOnFailureListener {
                result = false
            }
        return result
    }

}










