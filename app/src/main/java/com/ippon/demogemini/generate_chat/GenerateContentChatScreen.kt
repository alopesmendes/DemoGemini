package com.ippon.demogemini.generate_chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.type.asTextOrNull
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.RichText
import com.ippon.demogemini.core.CustomTextField
import com.ippon.demogemini.core.StateData
import com.ippon.demogemini.core.StateData.*

@Composable
fun GenerateContentChatScreen(
    chatState: ChatState = ChatState(),
    initialPrompt: String = "",
    onSend: (String) -> Unit,
) {
    var prompt by rememberSaveable {
        mutableStateOf(initialPrompt)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(chatState.history.size) { index ->
                val isModel by remember {
                    derivedStateOf {
                        chatState.history[index].role == "model"
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (isModel) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Icon bot"
                        )
                    }
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = if (isModel)
                                MaterialTheme.colorScheme.surfaceContainerHighest
                            else
                                MaterialTheme.colorScheme.surfaceContainerLowest,
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        RichText(modifier = Modifier.padding(8.dp)) {
                            Markdown(content = chatState
                                .history[index]
                                .parts
                                .mapNotNull { it.asTextOrNull() }
                                .joinToString { it },)
                        }
                    }
                }

            }
            item {
                when (chatState.stateData) {
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
                        chatState.errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                modifier = Modifier.padding(all = 8.dp)
                            )
                        }
                    }

                    Success -> {

                    }
                }
            }
        }
        CustomTextField(
            prompt = prompt,
            onValueChange = { prompt = it },
            onSend = onSend,
        )
    }

}