package com.example.interactivedigitaljournal.auth.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interactivedigitaljournal.auth.data.db.dao.UserDao
import com.example.interactivedigitaljournal.auth.data.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}