package com.example.eventexpress.data.remote

import com.example.eventexpress.domain.models.UserDataModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject

class UsersApi {
    private val fireStore = FirebaseFirestore.getInstance()
    private val userCollection = fireStore.collection("usersData")

    suspend fun getUserDataById(userId: String): UserDataModel? {
        var data : UserDataModel? = null
        userId.let {
            userCollection.document(userId)
                .get()
                .addOnSuccessListener { result ->
                    data = result.toObject<UserDataModel>()!!
                }
                .addOnFailureListener {
                    data = null
                }
        }
        return data
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










