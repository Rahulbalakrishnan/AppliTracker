package com.rahul.applitracker.database.room

data class ApplicationState(
    val applications: List<Application> = emptyList(),
    val applicationId: String = "",
    val companyName: String = "",
    val jobPosition: String = "",
    val jobUrl: String = "",
    val status: String ="",
    val date: String = "",
    val notes: String = "",
    val isAddingApplication: Boolean = false,
    val sortType: SortType = SortType.DATE_SORT
)
