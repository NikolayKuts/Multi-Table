package com.example.multi_table.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.multi_table.R
import com.example.multi_table.domain.MultiplicationExpression
import com.example.multi_table.domain.Timer
import com.example.multi_table.presentation.theme.MainTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                setContent {
                    MainTheme {
                        window.statusBarColor = MainTheme.colors.materialColors.primary.toArgb()

                        Surface() {
                            when (state) {
                                is MainState.StartState -> {
                                    BeginningView()
                                }
                                is MainState.QuestionedState -> {
                                    QuestionView(expression = state.expression, time = state.time)
                                }
                                is MainState.ResultState -> {
                                    if (state.expression != null) {
                                        ResultView(expression = state.expression, time = state.time)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BeginningView() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                onClick = { viewModel.sendEvent(event = MainEvent.NextExpression) }
            ) {
                Text(text = stringResource(R.string.button_text_start))
            }
        }
    }

    @Composable
    private fun QuestionView(expression: MultiplicationExpression, time: StateFlow<Timer.Time>) {
        val timeState = time.collectAsState()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            TimeView(seconds = timeState.value.seconds, millis = timeState.value.millis)

            ExpressionElements(
                expression = expression,
                modifier = Modifier.align(alignment = Alignment.Center)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
                onClick = { viewModel.sendEvent(event = MainEvent.ExpressionResult) }
            ) {
                Text(text = stringResource(R.string.button_text_result))
            }
        }
    }

    @Composable
    private fun ResultView(expression: MultiplicationExpression, time: StateFlow<Timer.Time>) {
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
                ResultViewButton(text = stringResource(R.string.button_text_wrong)) {
                    viewModel.sendEvent(event = MainEvent.WrongAnswer)
                }

                Spacer(modifier = Modifier.size(size = 16.dp))

                ResultViewButton(text = stringResource(R.string.button_text_next)) {
                    viewModel.sendEvent(event = MainEvent.NextExpression)
                }
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
    private fun ExpressionElements(
        expression: MultiplicationExpression,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExpressionElement(text = expression.multiplicand.toString())

            Text(
                text = stringResource(R.string.text_multiplication_sign),
                modifier = Modifier.padding(8.dp),
                style = MainTheme.typographies.multiplicationSignTextStyle
            )

            ExpressionElement(text = expression.multiplier.toString())
        }
    }

    @Composable
    fun ExpressionElement(text: String) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            style = MainTheme.typographies.expressionTextStyle
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

    @Composable
    private fun BoxScope.TimeView(seconds: Int, millis: Int) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(top = (LocalConfiguration.current.screenHeightDp / 16).dp)
        ) {
            Text(
                text = seconds.toString(),
                style = MainTheme.typographies.timerTextStyle
            )
            Text(
                text = stringResource(R.string.text_colon),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .offset(y = (-2.5).dp),
                style = MainTheme.typographies.timerTextStyle
            )
            Text(
                text = millis.toString(),
                style = MainTheme.typographies.timerTextStyle
            )
        }
    }
}