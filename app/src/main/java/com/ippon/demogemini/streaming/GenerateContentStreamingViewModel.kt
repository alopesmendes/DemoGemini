package com.ippon.demogemini.streaming

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.ippon.demogemini.core.SummarizeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenerateContentStreamingViewModel(
    private val generativeModel: GenerativeModel,
    private val generativeModelVision: GenerativeModel,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SummarizeUiState> =
        MutableStateFlow(SummarizeUiState.Initial)
    val uiState: StateFlow<SummarizeUiState> =
        _uiState.asStateFlow()

    fun generateContentStream(
        imageBitmap: Bitmap? = null,
        prompt: String,
    ) {
        _uiState.update {
            SummarizeUiState.Loading
        }

        viewModelScope.launch {
            val contentStream = if (imageBitmap == null) {
                generativeModel.generateContentStream(prompt)
            } else {
                generativeModelVision.generateContentStream(
                    content {
                        image(imageBitmap)
                        text(prompt)
                    }
                )
            }
            contentStream.collect { outputContent ->
                _uiState.update { oldState ->
                    val text = outputContent.text ?: ""
                    SummarizeUiState.Success(
                        latestOutputText = text,
                        outputTexts = listOf(*oldState.outputTexts.toTypedArray(), text),
                    )
                }
            }
        }
    }
}