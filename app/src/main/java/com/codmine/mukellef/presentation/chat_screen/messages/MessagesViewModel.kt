package com.codmine.mukellef.presentation.chat_screen.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.use_case.chat_screen.*
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_ID
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_NAME
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val addListener: AddListener,
    private val removeListener: RemoveListener,
    private val savedStateHandle: SavedStateHandle,
    private val getUserLoginData: GetUserLoginData,
    private val postMessage: PostMessage
):ViewModel() {
    var uiState by mutableStateOf(MessagesScreenDataState())
        private set

    private val _uiEventChannel = Channel<MessagesUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    private val _appSettings = mutableStateOf(AppSettings())

    private var _receiverId: String = ""
    private var _receiverName: String = ""
    private var _chatKey: String = ""

    fun onEvent(event: MessagesEvent) {
        when(event) {
            is MessagesEvent.AddMessagesListener -> {
                _receiverId = savedStateHandle.get<String>(NAV_CHAT_MESSAGES_USER_ID) ?: ""
                _receiverName = savedStateHandle.get<String>(NAV_CHAT_MESSAGES_USER_NAME) ?: ""
                getAppSettings()
                initializeDataState()
                addChatListener()
            }
            is MessagesEvent.MessageChanged -> {
                uiState = uiState.copy(message = event.messageValue)
            }
            is MessagesEvent.PostMessage -> { sendMessage(event.message) }
            is MessagesEvent.RemoveMessagesListener -> { removeChatListener() }
        }
    }

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

    private fun initializeDataState() {
        uiState = MessagesScreenDataState(
            userId = _appSettings.value.user,
            receiverId = _receiverId,
            receiverName = _receiverName
        )
        _chatKey = if(_appSettings.value.user < _receiverId) "f${_appSettings.value.user}-s$_receiverId" else "f$_receiverId-s${_appSettings.value.user}"
    }

    private fun addChatListener() {
        viewModelScope.launch {
            addListener(_appSettings.value.gib, _appSettings.value.user, _receiverId, _chatKey, ::onMessageAddEvent, ::onMessageAddError)
        }
    }

    private fun onMessageAddEvent(message: Message) {
        val newList = uiState.messages.toMutableList()
        if(newList.size > 0) {
            if(message.time > newList[0].time) newList.add(0, message) else newList.add(message)
        } else {
            newList.add(message)
        }
        uiState = uiState.copy(
            isLoading = false,
            errorStatus = false,
            messages = newList
        )
    }

    private fun onMessageAddError(error: Throwable) {
        uiState = uiState.copy(
            isLoading = false,
            errorStatus = true,
            errorText = error.localizedMessage?.let { UiText.DynamicString(it) },
            messages = emptyList()
        )
    }

    private fun sendMessage(sentMessage: String) {
        postMessage(_appSettings.value.gib, _appSettings.value.user, _receiverId, _chatKey, sentMessage) { error ->
            if(error == null) {
                uiState = uiState.copy(message = "")
                viewModelScope.launch {
                    _uiEventChannel.send(MessagesUiEvent.SendMessageSuccess)
                }
            } else onSendMessageError(error)
        }
    }

    private fun onSendMessageError(error: Throwable) {
        viewModelScope.launch {
            _uiEventChannel.send(MessagesUiEvent.ShowSnackbar(error.let { UiText.DynamicString(it.toString()) }))
        }
    }

    private fun removeChatListener() {
        viewModelScope.launch {
            removeListener()
        }
    }
}