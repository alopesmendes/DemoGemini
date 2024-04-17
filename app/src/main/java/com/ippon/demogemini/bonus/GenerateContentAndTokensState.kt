package com.ippon.demogemini.bonus

import com.ippon.demogemini.core.StateData

data class GenerateContentAndTokensState(
    val stateData: StateData = StateData.Initial,
    val outputText: String = "",
    val totalTokens: Int = 0,
    val errorMessage: String? = null,
)
