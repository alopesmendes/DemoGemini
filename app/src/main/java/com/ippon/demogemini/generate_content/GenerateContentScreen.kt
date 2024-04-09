package com.ippon.demogemini.generate_content

import androidx.compose.runtime.Composable
import com.ippon.demogemini.core.SummarizeUiState
import com.ippon.demogemini.core.SummarizeContainer

@Composable
fun GenerateContentScreen(
    uiState: SummarizeUiState = SummarizeUiState.Initial,
    onSendPrompt: (String) -> Unit = {}
) {
    SummarizeContainer(uiState = uiState, onSend = { _, s -> onSendPrompt(s) },)
}