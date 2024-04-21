package com.ippon.demogemini.generate_content_image

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import com.ippon.demogemini.core.SummarizeUiState
import com.ippon.demogemini.core.SummarizeContainer

@Composable
fun GenerateContentImageScreen(
    uiState: SummarizeUiState,
    onSend: (Bitmap, String) -> Unit,
) {
    SummarizeContainer(
        uiState = uiState,
        isImagePickerEnabled = true,
        initialPrompt = "can you tell me what you see in this image",
        onSend = { bitmap, s ->
            bitmap?.let {
                onSend(it, s)
            }
        },
    )
}