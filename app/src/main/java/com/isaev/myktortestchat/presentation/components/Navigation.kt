package com.isaev.myktortestchat.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.isaev.myktortestchat.presentation.screen.chat.ChatScreen
import com.isaev.myktortestchat.presentation.screen.connectToChat.ConnectChatScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ConnectChatScreen.route
    ) {
        composable(route = Screen.ConnectChatScreen.route) {
            ConnectChatScreen(onNavigate = navController::navigate)
        }

        composable(
            route = Screen.ChatScreen.route + "/{username}",
            arguments = listOf(
                navArgument(
                    name = "username"
                ) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val username = it.arguments?.getString("username")
            ChatScreen(username = username)
        }
    }
}