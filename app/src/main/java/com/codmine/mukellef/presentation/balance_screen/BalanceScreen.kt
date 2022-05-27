package com.codmine.mukellef.presentation.balance_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BalanceScreen(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        Text("Balance")
    }
}