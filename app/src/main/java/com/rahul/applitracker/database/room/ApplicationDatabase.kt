package com.rahul.applitracker.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahul.applitracker.database.room.Application
import com.rahul.applitracker.database.room.ApplicationDao

@Database(
    entities = [Application::class],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val dao: ApplicationDao
}