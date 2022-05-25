package com.codmine.mukellef.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codmine.mukellef.presentation.components.OfisScaffold
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.ui.theme.MobilOfisTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobilOfisTheme {
                ProvideWindowInsets(
                    windowInsetsAnimationsEnabled = true,
                    consumeWindowInsets = false) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface,
                    ) {
                        val navController = rememberNavController()
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        OfisScaffold(
                            navController = navController,
                            showBars = shouldShowTopAndBottomBar(navBackStackEntry),
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }

    private fun shouldShowTopAndBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
        return backStackEntry?.destination?.route in listOf(
            Screen.Notification.route,
            Screen.Document.route,
            Screen.ChatPerson.route,
            Screen.Balance.route
        )
    }
}