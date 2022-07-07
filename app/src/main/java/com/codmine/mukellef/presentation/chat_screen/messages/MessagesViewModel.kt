package com.codmine.mukellef.presentation.chat_screen.messages

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.use_case.chat_screen.GetMessagesById
import com.codmine.mukellef.domain.use_case.chat_screen.PostMessage
import com.codmine.mukellef.domain.use_case.chat_screen.PostMessageReadingInfo
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_ID
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_NAME
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.util.UiText
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

    private val _dataState = mutableStateOf(MessagesScreenDataState())
    val dataState: MutableState<MessagesScreenDataState> = _dataState

    private val _appSettings = mutableStateOf(AppSettings())

    private var _receiverId: String = ""
    private var _receiverName: String = ""

    fun onEvent(event: MessagesEvent, context: Context) {
        when(event) {
            is MessagesEvent.LoadData -> {
                _receiverId = savedStateHandle.get<String>(NAV_CHAT_MESSAGES_USER_ID) ?: ""
                _receiverName = savedStateHandle.get<String>(NAV_CHAT_MESSAGES_USER_NAME) ?: ""
                getAppSettings(context)
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
                _dataState.value = _dataState.value.copy(message = event.messageValue)
            }
            is MessagesEvent.PostMessage -> {
                postMessage(event.message)
            }
        }
    }

    private fun postMessage(sentMessage: String) {
        viewModelScope.launch {
            val result = postMessage(
                _appSettings.value.gib,
                _appSettings.value.vk,
                _appSettings.value.password,
                _appSettings.value.user,
                _receiverId,
                sentMessage
            )
            when(result) {
                is Resource.Success -> {
                    _dataState.value = _dataState.value.copy(
                        isLoading = false,
                        messages = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _dataState.value = _dataState.value.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = ((result.message ?: UiText.StringResources(R.string.unexpected_error)))
                    )
                }
                is Resource.Loading -> {
                    _dataState.value = _dataState.value.copy(isLoading = true)
                }
            }
        }
        _dataState.value = _dataState.value.copy(message = "")
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
                    _dataState.value = _dataState.value.copy(
                        isLoading = false,
                        messages = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _dataState.value = _dataState.value.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = ((result.message ?: UiText.StringResources(R.string.unexpected_error)))
                    )
                }
                is Resource.Loading -> {
                    _dataState.value = _dataState.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initializeDataState() {
        _dataState.value = MessagesScreenDataState(
            userId = _appSettings.value.user,
            receiverId = _receiverId,
            receiverName = _receiverName
        )
    }

    private fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}