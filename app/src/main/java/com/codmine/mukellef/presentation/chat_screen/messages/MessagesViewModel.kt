package com.codmine.mukellef.presentation.chat_screen.messages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
):ViewModel() {

    init {
        val symbol = savedStateHandle.get<String>(NAV_CHAT_MESSAGES)

        println(symbol)
    }

}