package com.ippon.demogemini.generate_content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.ippon.demogemini.core.SummarizeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenerateContentViewModel(
    private val generativeModel: GenerativeModel
): ViewModel() {
    private val _uiState: MutableStateFlow<SummarizeUiState> =
        MutableStateFlow(SummarizeUiState.Initial)
    val uiState: StateFlow<SummarizeUiState> =
        _uiState.asStateFlow()

    fun generateContent(prompt: String) {
        _uiState.update { SummarizeUiState.Loading }

        viewModelScope.launch {
            try {
                val content = generativeModel.generateContent(prompt)
                _uiState.update { SummarizeUiState.Success(content.text ?: "") }
            } catch (e: Exception) {
                _uiState.update { SummarizeUiState.Error(e.message ?: "") }
            }
        }
    }
}