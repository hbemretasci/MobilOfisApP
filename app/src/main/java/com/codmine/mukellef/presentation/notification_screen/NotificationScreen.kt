package com.codmine.mukellef.presentation.notification_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.domain.model.notifications.Notification
import com.codmine.mukellef.presentation.components.GlowIndicator
import com.codmine.mukellef.presentation.components.ReLoadData
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NotificationScreen(
    paddingValues: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val state = viewModel.dataState.value
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(NotificationEvent.LoadData, context)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.onEvent(NotificationEvent.Refresh, context) },
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
                items(state.notifications) { notification ->
                    NotificationItem(
                        notification = notification,
                        onItemClick = { }
                    )
                }
            }
        }
        if(state.error.isNotBlank()) {
            ReLoadData(
                modifier = Modifier.fillMaxSize(),
                errorMsg = state.error,
                onRetry = {viewModel.onEvent(NotificationEvent.LoadNotifications, context) }
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onItemClick: (Notification) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(notification) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${notification.message}. ${notification.senderUser} (${notification.readingTime})",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
    }
}