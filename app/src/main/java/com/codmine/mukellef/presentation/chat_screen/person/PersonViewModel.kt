package com.codmine.mukellef.presentation.chat_screen.person

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.model.chat.UnreadNotification
import com.codmine.mukellef.domain.use_case.chat_screen.GetRelatedUsers
import com.codmine.mukellef.domain.use_case.chat_screen.GetUnreadMessagesCount
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.login_screen.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getRelatedUsers: GetRelatedUsers,
    private val getUnreadMessagesCount: GetUnreadMessagesCount,
    private val getUserLoginData: GetUserLoginData
): ViewModel() {
    var state by mutableStateOf(PersonScreenDataState())

    private val _uiEventChannel = Channel<PersonUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    private val _appSettings = mutableStateOf(AppSettings())

    private var unReadList: List<UnreadNotification>? = emptyList()

    fun onEvent(event: PersonEvent, context: Context) {
        when(event) {
            is PersonEvent.LoadData -> {
                getAppSettings(context)
                getChatPersons()
            }
            is PersonEvent.Refresh -> {
                getChatPersons()
            }
            is PersonEvent.NavigateUser -> {
                viewModelScope.launch {
                    _uiEventChannel.send(PersonUiEvent.Navigate)
                }
            }
        }
    }

    private fun getChatPersons() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val unRead = getUnreadMessagesCount(
                _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user
            )
            when(unRead) {
                is Resource.Success -> {
                    when(val persons = getRelatedUsers(
                        _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, unRead.data ?: emptyList()
                    )) {
                        is Resource.Success -> {
                            state = state.copy(
                                relatedUsers = persons.data ?: emptyList(),
                                isLoading = false,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                relatedUsers = null,
                                isLoading = false,
                                error = persons.message
                            )
                        }
                        else -> Unit
                    }
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

    private fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }
}