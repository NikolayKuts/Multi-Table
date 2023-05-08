package com.kuts.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    state: MainState,
    onStartButtonClick: () -> Unit,
    onResultButtonClick: () -> Unit,
    onWrongButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        val minContainerHeight = when (state) {
            is MainState.StartState -> 100.dp
            is MainState.QuestionedState -> 250.dp
            is MainState.ResultState -> 280.dp
        }

        item {
            Box(
                modifier = Modifier
                    .heightIn(min = minContainerHeight)
                    .fillParentMaxSize()
            ) {
                when (state) {
                    is MainState.StartState -> {
                        BeginningView(onStartButtonClick = onStartButtonClick)
                    }
                    is MainState.QuestionedState -> {
                        QuestionView(
                            screenState = state,
                            onResultButtonClick = onResultButtonClick
                        )
                    }
                    is MainState.ResultState -> {
                        ResultView(
                            state = state,
                            onWrongButtonClick = onWrongButtonClick,
                            onNextButtonClick = onNextButtonClick,
                        )
                    }
                }
            }
        }
    }
}