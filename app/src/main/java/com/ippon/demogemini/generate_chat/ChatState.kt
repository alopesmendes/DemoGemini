package com.ippon.demogemini.generate_chat

import com.google.ai.client.generativeai.type.Content

enum class StateData {
    Initial, Loading, Error, Success
}

data class ChatState(
    val history: List<Content> = emptyList(),
    val stateData: StateData = StateData.Initial,
    val errorMessage: String? = null,
)
