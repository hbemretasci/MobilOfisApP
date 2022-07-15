package com.codmine.mukellef.presentation.document_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.documents.Document
import com.codmine.mukellef.domain.util.Constants.XL_ROUNDED_VALUE
import com.codmine.mukellef.domain.util.dateAndTime
import com.codmine.mukellef.presentation.components.DataNotFound
import com.codmine.mukellef.presentation.components.GlowIndicator
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun DocumentScreen(
    paddingValues: PaddingValues,
    viewModel: DocumentViewModel = hiltViewModel()
) {
    val state = viewModel.dataState.value
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(DocumentEvent.LoadData)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(DocumentEvent.Refresh) },
            indicator = { state, trigger ->
                GlowIndicator(
                    swipeRefreshState = state,
                    refreshTriggerDistance = trigger
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.large)) }
                items(state.documents) { document ->
                    DocumentItem(
                        document = document,
                        readingStatus = document.readingTime.isNotEmpty(),
                        onDocumentClick = {
                            viewModel.onEvent(DocumentEvent.ShowAndReadDocument(it))
                        }
                    )
                }
            }
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if(state.errorStatus) {
            ReLoadData(
                modifier = Modifier.fillMaxSize(),
                errorMsg = state.errorText ?: UiText.StringResources(R.string.unexpected_error),
                onRetry = { viewModel.onEvent(DocumentEvent.Refresh) }
            )
        }
        if((!state.isLoading) && (!state.errorStatus) && (state.documents.isEmpty())) {
            DataNotFound(message = UiText.StringResources(R.string.document_not_found))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentItem(
    document: Document,
    readingStatus: Boolean,
    onDocumentClick: (Document) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.small
            )
            .clickable {
                onDocumentClick(document)
            },
        shape = RoundedCornerShape(XL_ROUNDED_VALUE).copy(
            topStart = CornerSize(0),
            bottomEnd = CornerSize(0)
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = document.message,
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.medium
                ),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.weight(.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = UiText.StringResources(R.string.document_title_post_time).asString(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = dateAndTime(document.postTime),
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(.5f)
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = UiText.StringResources(R.string.document_title_read_time).asString(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = if (document.readingTime.isNotEmpty()) dateAndTime(document.readingTime) else "-" ,
                            modifier = Modifier.padding(MaterialTheme.spacing.small),
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.small
                    ),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DocumentStatusIcon(readingStatus)
            }
        }
    }
}

@Composable
fun DocumentStatusIcon(documentStatus: Boolean) {
    Icon(
        tint = if (documentStatus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        imageVector = if(documentStatus) Icons.Default.MarkEmailRead else Icons.Default.MarkEmailUnread,
        contentDescription = UiText.StringResources(R.string.document_icon_content_description).asString()
    )
}