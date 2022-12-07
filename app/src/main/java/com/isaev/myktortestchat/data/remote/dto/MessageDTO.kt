package com.isaev.myktortestchat.data.remote.dto

import com.isaev.myktortestchat.domain.model.Message
import java.text.DateFormat
import java.util.Date

data class MessageDTO(
    val text: String,
    val timestamp: Long,
    val username: String,
    val id: String
) {

    fun toMessage(): Message {
        val date =  Date(timestamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)

        return Message(
            text = text,
            time = formattedDate,
            username = username
        )
    }

}
