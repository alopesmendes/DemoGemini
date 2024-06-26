package com.ippon.demogemini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.ai.client.generativeai.GenerativeModel
import com.ippon.demogemini.core.NavigationHost
import com.ippon.demogemini.ui.theme.DemoGeminiTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.apiKey
        )
        val chat = generativeModel.startChat()
        setContent {
            DemoGeminiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavigationHost(
                        generativeModel = generativeModel,
                        chat = chat,
                    )
                }
            }
        }
    }
}