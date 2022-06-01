package com.codmine.mukellef.presentation.document_screen

import com.codmine.mukellef.domain.model.documents.Document

data class DocumentScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val documents: List<Document> = emptyList(),
    val error: String = ""
)