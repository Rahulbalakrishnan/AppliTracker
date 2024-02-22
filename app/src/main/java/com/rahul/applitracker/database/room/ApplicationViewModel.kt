package com.rahul.applitracker.database.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationViewModel(
    private val dao: ApplicationDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.DATE_SORT)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _applications = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.DATE_SORT -> dao.getApplicationOrderByDate()
            SortType.ID_SORT -> dao.getApplicationOrderByApplicationId()
            SortType.STATUS_SORT -> dao.getApplicationOrderByStatus()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ApplicationState())


    val state = combine(_state, _sortType, _applications) { state, sortType, applications ->
        state.copy(
            applications = applications,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ApplicationState())

    fun onEvent(event: ApplicationEvent) {
        when (event) {
            is ApplicationEvent.DeleteApplication -> {
                viewModelScope.launch {
                    dao.deleteApplication(event.application)
                }

            }

            ApplicationEvent.SaveApplication -> {
                val applicationId = state.value.applicationId
                val companyName = state.value.companyName
                val jobPosition = state.value.jobPosition
                val jobUrl = state.value.jobUrl
                val status = state.value.status
                val date = state.value.date
                val notes = state.value.notes

                if (
                    applicationId.isBlank()
                    || companyName.isBlank()
                    || jobPosition.isBlank()
                    || jobUrl.isBlank()
                ) {
                    return
                }
                val application = Application(
                    applicationId = applicationId,
                    companyName = companyName,
                    jobPosition = jobPosition,
                    jobUrl = jobUrl,
                    status = status,
                    date = date,
                    notes = notes,
                )
                viewModelScope.launch {
                    dao.upsertApplication(application)
                }
                _state.update {
                    it.copy(
                        isAddingApplication = false,
                        applicationId = "",
                        companyName = "",
                        jobPosition = "",
                        jobUrl = "",
                        status = "",
                        date = "",
                        notes = "",
                    )
                }
            }

            ApplicationEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingApplication = false
                    )
                }
            }

            is ApplicationEvent.SetApplicationId -> {
                _state.update {
                    it.copy(
                        applicationId = event.applicationId
                    )
                }
            }

            is ApplicationEvent.SetCompanyName -> {
                _state.update {
                    it.copy(
                        companyName = event.companyName
                    )
                }
            }

            is ApplicationEvent.SetDate -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is ApplicationEvent.SetJobPosition -> {
                _state.update {
                    it.copy(
                        jobPosition = event.jobPosition
                    )
                }
            }

            is ApplicationEvent.SetJobUrl -> {
                _state.update {
                    it.copy(
                        jobUrl = event.jobUrl
                    )
                }
            }

            is ApplicationEvent.SetNotes -> {
                _state.update {
                    it.copy(
                        notes = event.notes
                    )
                }
            }

            is ApplicationEvent.SetStatus -> {
                _state.update {
                    it.copy(
                        status = event.status
                    )
                }
            }

            is ApplicationEvent.SortApplications -> {
                _sortType.value = event.sortType
            }

            ApplicationEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingApplication = true
                    )
                }
            }

            else -> {}
        }
    }
}