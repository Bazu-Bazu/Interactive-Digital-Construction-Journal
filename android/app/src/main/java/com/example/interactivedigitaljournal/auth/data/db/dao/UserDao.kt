package com.example.interactivedigitaljournal.auth.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.interactivedigitaljournal.auth.data.db.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM userEntity LIMIT 1")
    fun findFirst(): UserEntity

    @Insert
    fun insert(user: UserEntity)

    @Query("DELETE FROM userEntity")
    fun deleteAll()
}