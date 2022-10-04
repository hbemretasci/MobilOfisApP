package com.codmine.mukellef.presentation.document_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.model.documents.Document
import com.codmine.mukellef.domain.use_case.document_screen.GetDocuments
import com.codmine.mukellef.domain.use_case.document_screen.PostDocumentReadingInfo
import com.codmine.mukellef.domain.use_case.files.ShowDocument
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val getDocuments: GetDocuments,
    private val postDocumentReadingInfo: PostDocumentReadingInfo,
    private val getUserLoginData: GetUserLoginData,
    private val showDocument: ShowDocument
): ViewModel() {
    private val _uiState = MutableStateFlow(DocumentScreenDataState())
    val uiState = _uiState.asStateFlow()

    private var documentReadingState by mutableStateOf(ReadingDocumentState())

    private val _appSettings = mutableStateOf(AppSettings())

    fun onEvent(event: DocumentEvent) {
        when(event) {
            is DocumentEvent.LoadData -> {
                getAppSettings()
                getDocumentList()
            }
            is DocumentEvent.Refresh -> {
                getDocumentList()
            }
            is DocumentEvent.ShowAndReadDocument -> {
                if (event.document.readingTime.isEmpty()) postReadingInfo(event.document)
                if (event.document.documentName.isNotEmpty()) showDocument(_appSettings.value.gib, event.document)
            }
        }
    }

    private fun showDocument(gib: String, document: Document) {
        showDocument(gib, document.documentName)
    }

    private fun getDocumentList() {
        getDocuments(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user, _appSettings.value.accountant
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        errorStatus = false,
                        documents = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        documents = emptyList()
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = true,
                        errorStatus = false,
                        documents = emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun postReadingInfo(document: Document) {
        postDocumentReadingInfo(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, document.id
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    documentReadingState = documentReadingState.copy(
                        isLoading = false,
                        errorStatus = false,
                        readingDocument = result.data
                    )
                    getDocumentList()
                }
                is Resource.Error -> {
                    documentReadingState = documentReadingState.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        readingDocument = null
                    )
                }
                is Resource.Loading -> {
                    documentReadingState = documentReadingState.copy(
                        isLoading = true,
                        errorStatus = false,
                        readingDocument = null
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