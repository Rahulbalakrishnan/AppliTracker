package com.rahul.applitracker.database.room

sealed interface ApplicationEvent {
    object SaveApplication : ApplicationEvent
    data class SetApplicationId(val applicationId: String) : ApplicationEvent
    data class SetCompanyName(val companyName: String) : ApplicationEvent
    data class SetJobPosition(val jobPosition: String) : ApplicationEvent
    data class SetJobUrl(val jobUrl: String) : ApplicationEvent
    data class SetStatus(val status: String) : ApplicationEvent
    data class SetDate(val date: String) : ApplicationEvent
    data class SetNotes(val notes: String) : ApplicationEvent
    object ShowDialog : ApplicationEvent
    object HideDialog : ApplicationEvent
    data class SortApplications(val sortType: SortType): ApplicationEvent
    data class DeleteApplication(val application: Application) : ApplicationEvent

}

