package com.isaev.myktortestchat.presentation.screen.connectToChat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectChatViewModel @Inject constructor() : ViewModel() {

    private val username_ = mutableStateOf("")
    val username: State<String> = username_

    private val onConnectToChat_ = MutableSharedFlow<String>()
    val onConnectToChat: SharedFlow<String> = onConnectToChat_.asSharedFlow()

    fun onChangeUsername(username: String) {
        username_.value = username
    }

    fun onJoinChatClick() {
        viewModelScope.launch {
            if (username.value.isNotBlank()) {
                onConnectToChat_.emit(username.value)
            }
        }
    }
}