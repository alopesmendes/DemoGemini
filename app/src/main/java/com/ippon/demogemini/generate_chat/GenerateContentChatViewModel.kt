package com.ippon.demogemini.generate_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.ippon.demogemini.core.SummarizeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Model gemini-pro-vision still not optimise for chat
class GenerateContentChatViewModel(
    private val chat: Chat,
): ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    init {
        loadChat()
    }

    private fun loadChat() {
        viewModelScope.launch {
            _chatState.update { state ->
                state.copy(
                    history = chat.history,
                    stateData = StateData.Success,
                )
            }
        }
    }

    fun generateChat(inputText: String) {
        _chatState.update { state ->
            state.copy(
                stateData = StateData.Loading,
            )
        }
        viewModelScope.launch {
            try {
                val responseMessage = chat.sendMessage(inputText)
                _chatState.update { state ->
                    state.copy(
                        stateData = StateData.Success
                    )
                }
            } catch (e: Exception) {
                _chatState.update { state ->
                    state.copy(
                        stateData = StateData.Error,
                        errorMessage = e.message,
                    )
                }
            }

        }
    }
}