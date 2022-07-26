package com.codmine.mukellef.presentation.chat_screen.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.use_case.chat_screen.GetMessagesById
import com.codmine.mukellef.domain.use_case.chat_screen.PostMessage
import com.codmine.mukellef.domain.use_case.chat_screen.PostMessageReadingInfo
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_ID
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_NAME
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val getMessagesById: GetMessagesById,
    private val savedStateHandle: SavedStateHandle,
    private val getUserLoginData: GetUserLoginData,
    private val postMessageReadingInfo: PostMessageReadingInfo,
    private val postMessage: PostMessage
):ViewModel() {
    var uiState by mutableStateOf(MessagesScreenDataState())
        private set

    private val _appSettings = mutableStateOf(AppSettings())

    private var _receiverId: String = ""
    private var _receiverName: String = ""

    fun onEvent(event: MessagesEvent) {
        when(event) {
            is MessagesEvent.LoadData -> {
                _receiverId = savedStateHandle.get<String>(NAV_CHAT_MESSAGES_USER_ID) ?: ""
                _receiverName = savedStateHandle.get<String>(NAV_CHAT_MESSAGES_USER_NAME) ?: ""
                getAppSettings()
                initializeDataState()
                getMessageList()
            }
            is MessagesEvent.Refresh -> {
                getMessageList()
            }
            is MessagesEvent.PostReadingMessage -> {
                readMessage(event.messageId)
            }
            is MessagesEvent.MessageChanged -> {
                uiState = uiState.copy(message = event.messageValue)
            }
            is MessagesEvent.PostMessage -> {
                postMessage(event.message)
            }
        }
    }

    private fun postMessage(sentMessage: String) {

        println("mesaj at")

        val result = postMessage(
            _appSettings.value.gib,
            _appSettings.value.user,
            _receiverId,
            sentMessage
        )
        println(result)
        when(result) {
            is Resource.Success -> {
                uiState = uiState.copy(
                    isLoading = false,
                    errorStatus = false,
                    messages = emptyList()
                )
            }
            is Resource.Error -> {
                uiState = uiState.copy(
                    isLoading = false,
                    errorStatus = true,
                    errorText = ((result.message ?: UiText.StringResources(R.string.unexpected_error))),
                    messages = emptyList()
                )
            }
            is Resource.Loading -> {
                uiState = uiState.copy(
                    isLoading = true,
                    errorStatus = false,
                    messages = emptyList()
                )
            }
        }
        uiState = uiState.copy(message = "")
    }

    private fun readMessage(messageId: String) {
        viewModelScope.launch {
            postMessageReadingInfo(
                _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, messageId
            )
        }
    }

    private fun getMessageList() {
        getMessagesById(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user, _receiverId
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = false,
                        messages = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        messages = emptyList()
                    )
                }
                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true,
                        errorStatus = false,
                        messages = emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initializeDataState() {
        uiState = MessagesScreenDataState(
            userId = _appSettings.value.user,
            receiverId = _receiverId,
            receiverName = _receiverName
        )
    }

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}