package com.ippon.demogemini.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import java.util.StringJoiner
import kotlin.random.Random

/**
 * A composable function that displays a text with a typewriter-like effect, revealing characters in chunks.
 *
 * @param text The input text to be displayed with the typewriter effect.
 * @param minDelayInMillis The minimum delay in milliseconds between revealing character chunks, defaults to 10ms.
 * @param maxDelayInMillis The maximum delay in milliseconds between revealing character chunks, defaults to 50ms.
 * @param minCharacterChunk The minimum number of characters to reveal at once, defaults to 1.
 * @param maxCharacterChunk The maximum number of characters to reveal at once, defaults to 5.
 * @param onEffectCompleted A callback function invoked when the entire text has been revealed.
 * @param displayTextComposable A composable function that receives the text to display with the typewriter effect.
 *
 * @throws IllegalArgumentException if [minDelayInMillis] is greater than [maxDelayInMillis].
 * @throws IllegalArgumentException if [minCharacterChunk] is greater than [maxCharacterChunk].
 */
@Composable
fun TypewriterTextEffect(
    modifier: Modifier = Modifier,
    text: String,
    minDelayInMillis: Long = 10,
    maxDelayInMillis: Long = 50,
    minCharacterChunk: Int = 1,
    maxCharacterChunk: Int = 5,
    onEffectCompleted: () -> Unit = {},
    displayTextComposable: @Composable (displayedText: String) -> Unit
) {
    val scrollState = rememberScrollState()

    // Initialize and remember the displayedText
    var currentText by remember(text) { mutableStateOf("") }
    val beginText by remember {
        mutableStateOf(StringJoiner(""))
    }

    LaunchedEffect(currentText) {
        scrollState.animateScrollTo(Int.MAX_VALUE)
    }

    // Call the displayTextComposable with the current displayedText value

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        displayTextComposable("$beginText$currentText")
    }

    // Launch the effect to update the displayedText value over time
    LaunchedEffect(text) {
        val textLength = text.length
        var endIndex = 0

        while (endIndex < textLength) {
            endIndex = minOf(
                endIndex + Random.nextInt(minCharacterChunk, maxCharacterChunk + 1),
                textLength
            )
            currentText = text.substring(startIndex = 0, endIndex = endIndex)
            delay(Random.nextLong(minDelayInMillis, maxDelayInMillis))
        }
        beginText.add(currentText)
        onEffectCompleted()
    }
}