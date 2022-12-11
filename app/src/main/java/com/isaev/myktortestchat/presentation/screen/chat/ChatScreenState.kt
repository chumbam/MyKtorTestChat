package com.isaev.myktortestchat.presentation.screen.chat

import com.isaev.myktortestchat.domain.model.Message

data class ChatScreenState(
    val message: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
