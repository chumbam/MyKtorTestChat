package com.isaev.myktortestchat.data.remote.dto

import com.isaev.myktortestchat.domain.model.Message
import java.text.DateFormat
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val username: String? = null,
    val message: String? = null,
    val sendTime: Long? = null,
    val id: String
) {

    fun toMessage(): Message {
        val date = sendTime?.let { Date(it) }
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date ?: 0)

        return Message(
            text = message,
            time = formattedDate,
            username = username
        )
    }

}
