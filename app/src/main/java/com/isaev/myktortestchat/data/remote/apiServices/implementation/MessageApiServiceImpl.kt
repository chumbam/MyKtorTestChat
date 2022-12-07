package com.isaev.myktortestchat.data.remote.apiServices.implementation


import com.isaev.myktortestchat.data.remote.apiServices.MessageApiService
import com.isaev.myktortestchat.data.remote.dto.MessageDTO
import com.isaev.myktortestchat.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*

class MessageApiServiceImpl(
    private val client: HttpClient
) : MessageApiService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDTO>>(MessageApiService.Endpoints.GetAllMessages.url)
                .map { it.toMessage() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}