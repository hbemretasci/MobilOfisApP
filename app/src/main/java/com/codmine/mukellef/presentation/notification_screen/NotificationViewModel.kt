package com.codmine.mukellef.presentation.notification_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        savedStateHandle.get<String>("gib")?.let { gib ->
            println(gib)
        }
        savedStateHandle.get<String>("vk")?.let { vk ->
            println(vk)
        }

    }
}