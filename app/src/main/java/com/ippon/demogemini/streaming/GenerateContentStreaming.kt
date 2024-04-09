package com.ippon.demogemini.streaming

import androidx.lifecycle.ViewModel
import com.google.ai.client.generativeai.GenerativeModel

class GenerateContentStreaming(
    private val generativeModel: GenerativeModel
): ViewModel() {
}