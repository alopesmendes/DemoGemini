package com.ippon.demogemini.generate_content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun GenerateContentRoute(
    generateContentViewModel: GenerateContentViewModel = viewModel(),
) {
    val uiState by generateContentViewModel.uiState.collectAsState()
    GenerateContentScreen(
        uiState = uiState,
        onSendPrompt = generateContentViewModel::generateContent,
    )
}