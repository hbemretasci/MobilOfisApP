package com.codmine.mukellef.presentation.balance_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.balance.Transaction
import com.codmine.mukellef.presentation.components.DataNotFound
import com.codmine.mukellef.presentation.components.GlowIndicator
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun BalanceScreen(
    paddingValues: PaddingValues,
    viewModel: BalanceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isRefreshing
    )

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(BalanceEvent.LoadData)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(BalanceEvent.Refresh) },
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
                items(uiState.transactions) { transaction ->
                    TransactionItem(transaction = transaction)
                }
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.large)) }
            }
        }
        if(uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if(uiState.errorStatus) {
            ReLoadData(
                modifier = Modifier.fillMaxSize(),
                errorMsg = uiState.errorText ?: UiText.StringResources(R.string.unexpected_error),
                onRetry = { viewModel.onEvent(BalanceEvent.Refresh) }
            )
        }
        if((!uiState.isLoading) && (!uiState.errorStatus) && (uiState.transactions.isEmpty())) {
            DataNotFound(message = UiText.StringResources(R.string.transaction_not_found))
        }
    }
}

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
                    start = MaterialTheme.spacing.small
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = transaction.dateTime,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(.7f),
                text = transaction.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
            Text(
                modifier = Modifier.weight(.3f),
                text = transaction.amount,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}