package com.ippon.demogemini.generate_content_image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun GenerateContentImageRoute(
    generateContentImageViewModel: GenerateContentImageViewModel = viewModel()
) {
    val uiState by generateContentImageViewModel.uiState.collectAsState()
    GenerateContentImageScreen(
        uiState = uiState,
        onSend = { bitmap, s ->
            generateContentImageViewModel.generateContentImage(
                prompt = s,
                image = bitmap,
            )
        },
    )
}