package com.ippon.demogemini.streaming

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GenerateContentStreamingRoute(
    generateContentStreamingViewModel: GenerateContentStreamingViewModel = viewModel()
) {
    val uiState by generateContentStreamingViewModel.uiState.collectAsState()
    GenerateContentStreamingScreen(
        uiState = uiState,
        onSend = generateContentStreamingViewModel::generateContentStream
    )
}