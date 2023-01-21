package com.example.multi_table.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.multi_table.R

@Composable
fun BeginningView(onStartButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .buttonPadding()
        ) {
            AppButton(textId = R.string.button_text_start, onClick = onStartButtonClick)
        }
    }
}