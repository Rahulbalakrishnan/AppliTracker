package com.rahul.applitracker.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Application(
    val applicationId: String,
    val companyName: String,
    val jobPosition: String,
    val jobUrl: String,
    val status: String,
    val date: String,
    val notes: String,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
)
