package com.example.multi_table.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.multi_table.R
import com.example.multi_table.domain.MultiplicationExpression
import com.example.multi_table.domain.Timer
import com.example.multi_table.presentation.theme.MultiTableTheme
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
                    MultiTableTheme {
                        when (state) {
                            is MainState.StartState -> {
                                StartContent()
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

////                Surface(
////                    modifier = Modifier.fillMaxSize(),
////                    color = MaterialTheme.colors.background
////                )
    }

    @Composable
    private fun StartContent() {
        setContent {
            MultiTableTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 28.dp, bottom = 24.dp),
                        shape = AbsoluteRoundedCornerShape(size = 16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8BC34A)),
                        onClick = { viewModel.sendEvent(event = MainEvent.NextExpression) }
                    ) {
                        Text(text = stringResource(R.string.button_text_start))
                    }
                }
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
                    .padding(start = 24.dp, end = 28.dp, bottom = 24.dp),
                shape = AbsoluteRoundedCornerShape(size = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8BC34A)),
                onClick = { viewModel.sendEvent(event = MainEvent.ExpressionResult) }
            ) {
                Text(text = stringResource(R.string.button_text_show))
            }
        }
    }

    @Composable
    private fun ResultView(expression: MultiplicationExpression, time: StateFlow<Timer.Time>) {
        val timeState = time.collectAsState()

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            TimeView(seconds = timeState.value.seconds, millis = timeState.value.millis)

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExpressionResultElements(expression = expression)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) {
                ResultViewButton(text = stringResource(R.string.button_text_wrong)) {
                    viewModel.sendEvent(event = MainEvent.WrongAnswer)
                }
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
            text = "=",
            modifier = Modifier.padding(start = 8.dp),
            color = Color(0x70217A7A),
            fontSize = 40.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = expression.product.toString(),
            modifier = Modifier.padding(start = 8.dp),
            color = Color(0x70217A7A),
            fontSize = 40.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
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
                text = "x",
                modifier = Modifier.padding(8.dp),
                color = Color(0xFFDFB538),
                fontSize = 28.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )

            ExpressionElement(text = expression.multiplier.toString())
        }
    }

    @Composable
    fun ExpressionElement(text: String) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF217A7A),
        )
    }

//    @Preview
//    @Composable
//    fun Preview() {
//        MultiTableTheme {
//            Surface(
//                modifier = Modifier.fillMaxSize(),
//                color = MaterialTheme.colors.background
//            ) {
//                QuestionView(
//                    expression = MultiplicationExpression(
//                        multiplicand = 8,
//                        multiplier = 7
//                    ),
//                    time = MutableStateFlow(Timer.Time(seconds = 23, millis = 10))
//                )
//            }
//
//        }
//    }

    @Composable
    private fun ResultViewButton(text: String, onClick: () -> Unit) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            shape = AbsoluteRoundedCornerShape(size = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(color = 0xFF8BC34A)
            ),
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
                fontStyle = FontStyle.Italic,
                fontSize = 28.sp,
                color = Color(0xFF768D76)
            )
            Text(
                text = ":",
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .offset(y = (-2.5).dp),
                fontStyle = FontStyle.Italic,
                fontSize = 28.sp,
                color = Color(0xFF768D76)
            )
            Text(
                text = millis.toString(),
                fontStyle = FontStyle.Italic,
                fontSize = 28.sp,
                color = Color(0xFF768D76)
            )
        }
    }
}