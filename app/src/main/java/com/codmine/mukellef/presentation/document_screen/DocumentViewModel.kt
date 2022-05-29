package com.codmine.mukellef.presentation.document_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.use_case.document_screen.GetDocuments
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val getDocuments: GetDocuments,
    private val getUserLoginData: GetUserLoginData
): ViewModel() {

    private val _dataState = mutableStateOf(DocumentScreenDataState())
    val dataState: State<DocumentScreenDataState> = _dataState

    private val _appSettings = mutableStateOf(AppSettings())

    private fun getDocumentList() {
        getDocuments(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user, _appSettings.value.accountant
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _dataState.value = DocumentScreenDataState(documents = result.data ?: emptyList() )
                }
                is Resource.Error -> {
                    _dataState.value = DocumentScreenDataState(error = result.message ?: "Beklenmeyen hata.")
                }
                is Resource.Loading -> {
                    _dataState.value = DocumentScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: DocumentEvent) {
        when(event) {
            is DocumentEvent.LoadData -> {
                getDocumentList()
            }
        }
    }

    fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}