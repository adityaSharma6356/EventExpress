package com.example.eventexpress.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventexpress.data.local.event.EventEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(
        userEntity: UserEntity
    )

    @Query("DELETE FROM userentity")
    suspend fun clearUserData()

    @Query(
        """
            SELECT *
            FROM userentity
            WHERE LOWER(id) == LOWER(:query) 
        """
    )
    suspend fun searchUserData(query: String): UserEntity
}