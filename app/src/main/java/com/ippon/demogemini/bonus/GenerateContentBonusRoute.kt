package com.ippon.demogemini.bonus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun GenerateContentBonusRoute(
    generateContentBonusViewModel: GenerateContentBonusViewModel = viewModel()
) {
    val contentAndTokensState by generateContentBonusViewModel.state.collectAsState()
    GenerateContentBonusScreen(
        uiState = contentAndTokensState,
        onSend = generateContentBonusViewModel::generateContentBonus,
    )
}