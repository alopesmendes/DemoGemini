package com.ippon.demogemini.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ippon.demogemini.R

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    prompt: String,
    onValueChange: (String) -> Unit,
    onSend: (String) -> Unit,
    supportingData: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = prompt,
        label = { Text(stringResource(R.string.summarize_label)) },
        placeholder = { Text(stringResource(R.string.summarize_hint)) },
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = leadingIcon,
        trailingIcon = {
            IconButton(onClick = { onSend(prompt) }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Prompt")
            }
        },
        supportingText = supportingData
    )
}