package com.codmine.mukellef.presentation.document_screen

import com.codmine.mukellef.domain.model.documents.Document

sealed class DocumentEvent{
    object LoadData: DocumentEvent()
    object LoadDocuments: DocumentEvent()
    object Refresh: DocumentEvent()
    data class ShowAndReadDocument(val document: Document): DocumentEvent()
}
