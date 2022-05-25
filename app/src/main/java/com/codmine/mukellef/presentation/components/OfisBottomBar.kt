package com.codmine.mukellef.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MarkChatUnread
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codmine.mukellef.R

sealed class BottomNavItem (val title: Int, val route: String, val icon: ImageVector, val cont: Int) {
    object Notification: BottomNavItem(R.string.bottom_title_notification, Screen.Notification.route, Icons.Filled.MarkChatUnread, R.string.bottom_title_notification)
    object Document: BottomNavItem(R.string.bottom_title_document, Screen.Document.route, Icons.Filled.Description, R.string.bottom_title_document)
    object Chat: BottomNavItem(R.string.bottom_title_chat, Screen.ChatPerson.route, Icons.Filled.QuestionAnswer, R.string.bottom_title_chat)
    object Balance: BottomNavItem(R.string.bottom_title_balance, Screen.Balance.route, Icons.Filled.Analytics, R.string.bottom_title_balance)
}

val bottomNavItems = listOf(
    BottomNavItem.Notification,
    BottomNavItem.Document,
    BottomNavItem.Chat,
    BottomNavItem.Balance
)

@Composable
fun OfisBottomBar(
    navController: NavController,
    barState: Boolean
) {
    AnimatedVisibility (
        visible = barState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomNavItems.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, stringResource(id =  screen.cont)) },
                    label = { Text(stringResource(id = screen.title), fontWeight = FontWeight.Bold) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors()
                )
            }
        }
    }
}