package com.isaev.myktortestchat.data.remote.apiServices

import com.isaev.myktortestchat.common.Resource
import com.isaev.myktortestchat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageWebSocketService {

    companion object {
        const val URL_BASE = "10.0.2.2:8082"
    }

    sealed class Endpoints(val url: String) {
        object ChatWebSocketConnection : Endpoints("$URL_BASE/chat-socket")
    }

    suspend fun initWebSocketSession(username: String): Resource<Unit>

    suspend fun sendMessage(message: String)

    suspend fun observeMessage(): Flow<Message>

    suspend fun closeWebSocketSession()

}