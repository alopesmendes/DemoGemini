package com.ippon.demogemini.generate_chat

import com.google.ai.client.generativeai.type.Content
import com.ippon.demogemini.core.StateData

data class ChatState(
    val history: List<Content> = emptyList(),
    val stateData: StateData = StateData.Initial,
    val errorMessage: String? = null,
)
