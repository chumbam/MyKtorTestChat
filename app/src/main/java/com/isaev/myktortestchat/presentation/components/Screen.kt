package com.isaev.myktortestchat.presentation.components

sealed class Screen(val route: String) {
    object ConnectChatScreen: Screen("connect_chat_screen")
    object ChatScreen: Screen("chat_screen")
}
