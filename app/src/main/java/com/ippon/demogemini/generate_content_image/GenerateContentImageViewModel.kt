package com.ippon.demogemini.generate_content_image

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

class GenerateContentImageViewModel(
    private val generativeModel: GenerativeModel,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SummarizeUiState> =
        MutableStateFlow(SummarizeUiState.Initial)
    val uiState: StateFlow<SummarizeUiState> =
        _uiState.asStateFlow()

    fun generateContentImage(
        prompt: String,
        image: Bitmap,
    ) {
        viewModelScope.launch {
            _uiState.update { SummarizeUiState.Loading }
            try {
                val content = generativeModel.generateContent(
                    // kotlin dsl builder
                    content {
                        image(image)
                        text(prompt)

                    }
                )
                _uiState.update {
                    SummarizeUiState.Success(
                        outputText = content.text ?: "",
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    SummarizeUiState.Error(
                        errorMessage = e.message ?: "",
                    )
                }
            }
        }
    }
}