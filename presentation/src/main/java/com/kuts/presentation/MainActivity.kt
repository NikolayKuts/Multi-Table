package com.kuts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import com.kuts.presentation.theme.MainTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)

        setContent {
            MainTheme {
                Surface {
                    window.statusBarColor = MainTheme.colors.materialColors.primary.toArgb()

                    MainScreen(
                        state = viewModel.state.collectAsState().value,
                        onStartButtonClick = { viewModel.sendEvent(event = MainEvent.NextExpression) },
                        onResultButtonClick = { viewModel.sendEvent(event = MainEvent.ExpressionResult) },
                        onWrongButtonClick = { viewModel.sendEvent(event = MainEvent.WrongAnswer) },
                        onNextButtonClick = { viewModel.sendEvent(event = MainEvent.NextExpression) }
                    )
                }
            }
        }
    }
}