package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.example.presentation.MainState.*
import com.example.presentation.theme.MainTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)

        viewModel.state.collectWhenStarted(lifecycleOwner = this) { state ->
            setContent {
                MainTheme {
                    Surface {
                        window.statusBarColor = MainTheme.colors.materialColors.primary.toArgb()
                        ScreenState(state = state)
                    }
                }
            }
        }
    }

    @Composable
    private fun ScreenState(state: MainState) {
        when (state) {
            is StartState -> {
                BeginningView(
                    onStartButtonClick = { viewModel.sendEvent(event = MainEvent.NextExpression) }
                )
            }
            is QuestionedState -> {
                QuestionView(
                    screenState = state,
                    onResultButtonClick = { viewModel.sendEvent(event = MainEvent.ExpressionResult) }
                )
            }
            is ResultState -> {
                ResultView(
                    state = state,
                    onWrongButtonClick = { viewModel.sendEvent(event = MainEvent.WrongAnswer) },
                    onNextButtonClick = { viewModel.sendEvent(event = MainEvent.NextExpression) }
                )
            }
        }

    }
}