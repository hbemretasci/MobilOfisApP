package com.codmine.mukellef.presentation.document_screen

import com.codmine.mukellef.domain.model.documents.ReadingDocument

data class ReadingDocumentState(
    val isLoading: Boolean = false,
    val readingDocument: ReadingDocument? = null,
    val error: String = ""
)
