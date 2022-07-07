package com.codmine.mukellef.presentation.document_screen

import com.codmine.mukellef.domain.model.documents.ReadingDocument
import com.codmine.mukellef.presentation.util.UiText

data class ReadingDocumentState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val readingDocument: ReadingDocument? = null
)
