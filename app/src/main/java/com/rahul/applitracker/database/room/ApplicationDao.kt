package com.rahul.applitracker.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rahul.applitracker.database.room.Application
import kotlinx.coroutines.flow.Flow


@Dao
interface ApplicationDao {

    @Upsert
    suspend fun upsertApplication(application: Application)

    @Delete
    suspend fun deleteApplication(application: Application)

    @Query("SELECT * FROM application ORDER BY applicationId ASC")
    fun getApplicationOrderByApplicationId(): Flow<List<Application>>

    @Query("SELECT * FROM application ORDER BY date DESC")
    fun getApplicationOrderByDate(): Flow<List<Application>>

    @Query("SELECT * FROM application ORDER BY status ASC")
    fun getApplicationOrderByStatus(): Flow<List<Application>>



}