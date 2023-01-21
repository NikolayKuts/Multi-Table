package com.example.multi_table.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.multi_table.R
import com.example.multi_table.domain.common.Timer
import com.example.multi_table.domain.entities.MultiplicationExpression
import kotlinx.coroutines.flow.StateFlow

@Composable
fun QuestionView(
    expression: MultiplicationExpression,
    time: StateFlow<Timer.Time>,
    onResultButtonClick: () -> Unit,
) {
    val timeState = time.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TimeView(seconds = timeState.value.seconds, millis = timeState.value.millis)

        ExpressionElements(
            expression = expression,
            modifier = Modifier.align(alignment = Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .buttonPadding(),
        ) {
            AppButton(textId = R.string.button_text_result, onClick = onResultButtonClick)
        }
    }
}