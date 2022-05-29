package com.codmine.mukellef.presentation.balance_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.domain.model.balance.Transaction
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun BalanceScreen(
    paddingValues: PaddingValues,
    viewModel: BalanceViewModel = hiltViewModel()
) {
    val state = viewModel.dataState.value
    val context = LocalContext.current
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.getAppSettings(context)
        viewModel.onEvent(BalanceEvent.LoadData)
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            viewModel.onEvent(BalanceEvent.LoadData)
            isRefreshing = false
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { isRefreshing = true }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium)) }
                items(state.transactions) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
        if(state.error.isNotBlank()) {
            ReLoadData(
                modifier = Modifier.fillMaxSize(),
                errorMsg = state.error,
                onRetry = {
                    viewModel.onEvent(BalanceEvent.LoadData)
                }
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = transaction.dateTime,
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = transaction.name,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = transaction.amount,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}