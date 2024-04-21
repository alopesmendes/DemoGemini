package com.ippon.demogemini.generate_chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun GenerateContentChatRoute(
    generateContentChatViewModel: GenerateContentChatViewModel = viewModel()
) {
    val chatState by generateContentChatViewModel.chatState.collectAsState()
    GenerateContentChatScreen(
        chatState = chatState,
        onSend = generateContentChatViewModel::generateChat,
        initialPrompt = "can you tell me you tell me about one piece"
    )
}