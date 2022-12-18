package com.isaev.myktortestchat.data.remote.apiServices.implementation

import com.isaev.myktortestchat.common.Resource
import com.isaev.myktortestchat.data.remote.apiServices.MessageWebSocketService
import com.isaev.myktortestchat.data.remote.dto.MessageDTO
import com.isaev.myktortestchat.domain.model.Message
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MessageWebSocketServiceImpl(
    private val client: HttpClient
) : MessageWebSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initWebSocketSession(username: String): Resource<Unit> {

        return try {
            socket = client.webSocketSession {
                url("${MessageWebSocketService.Endpoints.ChatWebSocketConnection.url}?username=$username")

            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error(message = "Couldn't establish socket connection")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.localizedMessage ?: "Unknown Error")
        }

    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun observeMessage(): Flow<Message> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val format = Json { ignoreUnknownKeys = true }
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDTO = format.decodeFromString<MessageDTO>(string = json)
                    messageDTO.toMessage()
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override suspend fun closeWebSocketSession() {
        try {
            socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}