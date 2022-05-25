package com.codmine.mukellef.presentation.notification_screen

import android.accounts.Account
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NotificationScreen(
    gib: String, vk: String, password: String, user: String, accountant: String,
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(gib)
            Text(vk)
            Text(password)
            Text(user)
            Text(accountant)
        }
    }
}