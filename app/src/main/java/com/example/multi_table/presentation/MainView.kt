package com.example.multi_table.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.multi_table.R
import com.example.multi_table.domain.common.Timer
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.presentation.theme.MainTheme
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ResultView(
    expression: MultiplicationExpression,
    time: StateFlow<Timer.Time>,
    onWrongButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    val timeState = time.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TimeView(seconds = timeState.value.seconds, millis = timeState.value.millis)

        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExpressionResultElements(expression = expression)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            ResultViewButton(
                text = stringResource(R.string.button_text_wrong),
                onClick = onWrongButtonClick
            )

            Spacer(modifier = Modifier.size(size = 16.dp))

            ResultViewButton(
                text = stringResource(R.string.button_text_next),
                onClick = onNextButtonClick
            )
        }
    }
}

@Composable
private fun ExpressionResultElements(expression: MultiplicationExpression) {
    ExpressionElements(expression = expression)

    Text(
        text = stringResource(R.string.text_equal_sign),
        modifier = Modifier.padding(start = 8.dp),
        style = MainTheme.typographies.expressionResultTextStyle
    )

    Text(
        text = expression.product.toString(),
        modifier = Modifier.padding(start = 8.dp),
        style = MainTheme.typographies.expressionResultTextStyle
    )
}

@Composable
private fun ResultViewButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)
    ) {
        Text(text = text)
    }
}
