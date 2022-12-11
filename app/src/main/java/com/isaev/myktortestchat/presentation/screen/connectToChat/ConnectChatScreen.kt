package com.isaev.myktortestchat.presentation.screen.connectToChat

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ConnectChatScreen(
    viewModel: ConnectChatViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.onConnectToChat.collectLatest { username ->
            onNavigate("chat_screen/$username")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = viewModel.username.value,
                onValueChange = viewModel::onChangeUsername,
                placeholder = { Text(text = "Enter username...") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.onJoinChatClick() }) {
                Text(text = "Click to Join")

            }

        }
    }

}