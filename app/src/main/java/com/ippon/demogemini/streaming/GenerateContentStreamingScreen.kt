package com.ippon.demogemini.streaming

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import com.ippon.demogemini.core.SummarizeContainer
import com.ippon.demogemini.core.SummarizeUiState

@Composable
fun GenerateContentStreamingScreen(
    uiState: SummarizeUiState,
    onSend: (Bitmap?, String) -> Unit,
) {
    SummarizeContainer(
        uiState = uiState,
        onSend = onSend,
        isImagePickerEnabled = true,
        isStreamingResponse = true,
    )
}