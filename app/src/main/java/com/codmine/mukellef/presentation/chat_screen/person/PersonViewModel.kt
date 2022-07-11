package com.codmine.mukellef.presentation.chat_screen.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.use_case.chat_screen.GetRelatedUsers
import com.codmine.mukellef.domain.use_case.chat_screen.GetUnreadMessagesCount
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getRelatedUsers: GetRelatedUsers,
    private val getUnreadMessagesCount: GetUnreadMessagesCount,
    private val getUserLoginData: GetUserLoginData
): ViewModel() {

    private val _dataState = mutableStateOf(PersonScreenDataState())
    val dataState: MutableState<PersonScreenDataState> = _dataState

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
        viewModelScope.launch {
            _dataState.value = PersonScreenDataState(isLoading = true)
            val unRead = getUnreadMessagesCount(
                _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user
            )
            when(unRead) {
                is Resource.Success -> {
                    when(val persons = getRelatedUsers(
                        _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, unRead.data ?: emptyList()
                    )) {
                        is Resource.Success -> {
                            _dataState.value = PersonScreenDataState(
                                isLoading = false,
                                relatedUsers = persons.data ?: emptyList(),
                            )
                        }
                        is Resource.Error -> {
                            _dataState.value = PersonScreenDataState(
                                errorStatus = true,
                                errorText = ((persons.message ?: UiText.StringResources(R.string.unexpected_error)))
                            )
                        }
                        else -> Unit
                    }
                }
                is Resource.Error -> {
                    _dataState.value = PersonScreenDataState(
                        errorStatus = true,
                        errorText = ((unRead.message ?: UiText.StringResources(R.string.unexpected_error)))
                    )
                }
                else -> Unit
            }
        }
    }

    /*
    private fun getChatPersonsAsync() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val persons = async {
                getRelatedUsers(_appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password)
            }
            val unRead = async {
                getUnreadMessagesCount(_appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user)
            }
            when(val result = persons.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        relatedUsers = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        relatedUsers = null,
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
            when(val result = unRead.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        unReadList = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        unReadList = null,
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> Unit
            }
        }
    }
     */

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }
}