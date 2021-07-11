package com.evgeniykim.mvvmdaggerretrofitrxandroid.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evgeniykim.mvvmdaggerretrofitrxandroid.model.Post
import com.evgeniykim.mvvmdaggerretrofitrxandroid.model.PostDao

@Database(entities = arrayOf(Post::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
}