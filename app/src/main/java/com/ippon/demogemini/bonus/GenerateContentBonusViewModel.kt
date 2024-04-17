package com.ippon.demogemini.bonus

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.ippon.demogemini.core.StateData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenerateContentBonusViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {
    private val _state = MutableStateFlow(GenerateContentAndTokensState())
    val state = _state.asStateFlow()

    fun generateContentBonus(
        image: Bitmap,
        prompt: String,
    ) {
        _state.update { oldState -> oldState.copy(stateData = StateData.Loading) }
        viewModelScope.launch {
            try {
                val content = content {
                    image(image)
                    text(prompt)
                }
                val (totalTokens) = generativeModel.countTokens(content)
                val response = generativeModel.generateContent(content)
                _state.update { oldState ->
                    oldState.copy(
                        stateData = StateData.Success,
                        outputText = response.text ?: "",
                        totalTokens = totalTokens,
                    )
                }
            } catch (e: Exception) {
                _state.update { oldState ->
                    oldState.copy(
                        stateData = StateData.Error,
                        errorMessage = e.message,
                    )
                }
            }

        }
    }
}