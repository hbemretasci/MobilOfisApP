package com.codmine.mukellef.presentation.chat_screen.person

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.use_case.chat_screen.GetRelatedUsers
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getRelatedUsers: GetRelatedUsers,
    private val getUserLoginData: GetUserLoginData
): ViewModel() {
    private val _uiState = MutableStateFlow(PersonScreenDataState())
    val uiState = _uiState.asStateFlow()

    private val _appSettings = mutableStateOf(AppSettings())

    fun onEvent(event: PersonEvent) {
        when(event) {
            is PersonEvent.LoadData -> {
                getAppSettings()
                getChatPersons()
            }
            is PersonEvent.Refresh -> {
                getChatPersons()
            }
        }
    }

    private fun getChatPersons() {
        getRelatedUsers(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        errorStatus = false,
                        relatedUsers = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        relatedUsers = emptyList()
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = true,
                        errorStatus = false,
                        relatedUsers = emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }
}