package com.isaev.myktortestchat.presentation.screen.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaev.myktortestchat.common.Resource
import com.isaev.myktortestchat.data.remote.apiServices.MessageApiService
import com.isaev.myktortestchat.data.remote.apiServices.MessageWebSocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageApiService,
    private val chatWebSocketService: MessageWebSocketService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageText = mutableStateOf<String>("")
    val messageText: State<String> = _messageText

    private val _chatState = mutableStateOf(ChatScreenState())
    val chatState: State<ChatScreenState> = _chatState

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent

    init {
        getAllMessage()
        val username = savedStateHandle.get<String>("username")?.let { username ->
            initChatSocketSession(username)

        }

    }

    private fun getAllMessage() {
        viewModelScope.launch {
            _chatState.value = chatState.value.copy(
                isLoading = true
            )
            val result = messageService.getAllMessages()
            _chatState.value = chatState.value.copy(
                message = result,
                isLoading = false
            )
        }
    }


    fun onMessageChange(message: String) {
        _messageText.value = message
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (messageText.value.isNotBlank()) {
                chatWebSocketService.sendMessage(messageText.value)
            }
        }
    }

    private fun initChatSocketSession(username: String) {
        viewModelScope.launch {
            val result = chatWebSocketService.initWebSocketSession(username)
            when (result) {
                is Resource.Success -> {
                    chatWebSocketService.observeMessage()
                        .onEach { message ->
                            val newMessageList = chatState.value.message.toMutableList().apply {
                                add(0, message)
                            }
                            _chatState.value = chatState.value.copy(
                                message = newMessageList
                            )
                        }.launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    _toastEvent.emit(result.message ?: "Unknown Error")
                }
            }
        }
    }

    fun onDisconnectChat() {
        viewModelScope.launch {
            chatWebSocketService.closeWebSocketSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        onDisconnectChat()
    }
}