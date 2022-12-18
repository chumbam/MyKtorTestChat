package com.isaev.myktortestchat.presentation.screen.chat

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.isaev.myktortestchat.domain.model.Message
import com.isaev.myktortestchat.presentation.screen.chat.items.MessageBubble
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    username: String?,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { toast ->
            Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.initChatSocketSession()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.onDisconnectChat()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state = viewModel.chatState.value
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(state.message) { item: Message ->
                val isOwnMessage = item.username == username
                MessageBubble(isOwnMessage = isOwnMessage, message = item)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = viewModel.messageText.value,
                onValueChange = viewModel::onMessageChange,
                placeholder = {
                    Text(text = "Enter text...")
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = viewModel::sendMessage
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send Button")
            }
        }
    }
}