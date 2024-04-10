package com.ippon.demogemini.core

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText

@Composable
fun SummarizeContainer(
    uiState: SummarizeUiState,
    isStreamingResponse: Boolean = false,
    isImagePickerEnabled: Boolean = false,
    onSend: (Bitmap?, String) -> Unit,
) {
    var prompt by rememberSaveable {
        mutableStateOf("")
    }
    var outputText by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
        ) {
            when (uiState) {
                SummarizeUiState.Initial -> {
                    // Nothing is shown
                }

                SummarizeUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SummarizeUiState.Success -> {
                    Row(modifier = Modifier.padding(all = 8.dp)) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Person Icon"
                        )
                        if (isStreamingResponse) {
                            val outputTexts: SnapshotStateList<String> = remember {
                                mutableStateListOf("")
                            }
                            var index by rememberSaveable {
                                mutableIntStateOf(0)
                            }
                            LaunchedEffect(uiState.outputText) {
                                outputTexts.add(uiState.outputText.trimIndent())
                            }
                            TypewriterTextEffect(
                                text = outputTexts.getOrElse(index = index, defaultValue = { "" }),
                                onEffectCompleted = {
                                    index++
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                RichText {
                                    Markdown(it)
                                }
                            }
                        } else {
                            RichText(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                Markdown(uiState.outputText.trimIndent())
                            }
                        }

                    }
                }

                is SummarizeUiState.Error -> {
                    Text(
                        text = uiState.errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                }
            }
        }
        if (isImagePickerEnabled) {
            CustomTextImageField(
                isOnlyImagePrompt = !isStreamingResponse,
                prompt = prompt,
                onSend = { bitmap, s ->
                    onSend(bitmap, s)
                    prompt = ""
                },
                onValueChange = { prompt = it },
            )
        } else {
            CustomTextField(
                prompt = prompt,
                onSend = { s ->
                    onSend(null, s)
                    prompt = ""
                },
                onValueChange = { prompt = it },
            )
        }
    }

}