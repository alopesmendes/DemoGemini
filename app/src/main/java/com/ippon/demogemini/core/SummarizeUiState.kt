package com.ippon.demogemini.core

/**
 * A sealed hierarchy describing the state of the text generation.
 */
sealed class SummarizeUiState(
    open val latestOutputText: String = "",
    open val outputTexts: List<String> = emptyList(),
    open val errorMessage: String? = null,
) {

    /**
     * Empty state when the screen is first shown
     */
    data object Initial : SummarizeUiState()

    /**
     * Still loading
     */
    data object Loading : SummarizeUiState()

    /**
     * Text has been generated
     */
    data class Success(
        override val latestOutputText: String,
        override val outputTexts: List<String> = emptyList(),
    ) : SummarizeUiState(
        latestOutputText = latestOutputText,
        outputTexts = outputTexts
    )

    /**
     * There was an error generating text
     */
    data class Error(
        override val errorMessage: String?
    ) : SummarizeUiState(
        errorMessage = errorMessage,
    )
}