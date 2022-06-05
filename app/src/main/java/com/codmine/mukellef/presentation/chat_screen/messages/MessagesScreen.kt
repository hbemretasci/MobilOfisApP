package com.codmine.mukellef.presentation.chat_screen.messages

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codmine.mukellef.presentation.components.OfisTopBar

@Composable
fun MessagesScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: MessagesViewModel = hiltViewModel()
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OfisTopBar(
                navigation = { /*TODO*/ },
                title = { },
                actions = { }
            )

        }


    }


}