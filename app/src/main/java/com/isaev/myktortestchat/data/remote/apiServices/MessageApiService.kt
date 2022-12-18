package com.isaev.myktortestchat.data.remote.apiServices

import com.isaev.myktortestchat.domain.model.Message


interface MessageApiService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        const val URL_BASE = "http://10.0.3.2:8082"
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages : Endpoints("$URL_BASE/messages")
    }
}