package com.ippon.demogemini.bonus

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import com.ippon.demogemini.core.CustomTextImageField
import com.ippon.demogemini.core.StateData.Error
import com.ippon.demogemini.core.StateData.Initial
import com.ippon.demogemini.core.StateData.Loading
import com.ippon.demogemini.core.StateData.Success

@Composable
fun GenerateContentBonusScreen(
    uiState: GenerateContentAndTokensState,
    onSend: (Bitmap, String) -> Unit,
) {
    var prompt by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (uiState.stateData) {
                Initial -> {}
                Loading -> {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 8.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                Error -> {
                    uiState.errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.padding(all = 8.dp)
                        )
                    }
                }

                Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Total tokens for the current prompt: ${uiState.totalTokens}")
                        Row {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = "Person Icon"
                            )
                            RichText(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                Markdown(uiState.outputText.trimIndent())
                            }

                        }
                    }

                }
            }
        }
        CustomTextImageField(
            prompt = prompt,
            onValueChange = { prompt = it },
            onSend = { bitmap, s ->
                bitmap?.let {
                    onSend(it, s)
                }
                prompt = ""
            },
        )
    }
}