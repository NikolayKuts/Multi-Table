package com.example.multi_table.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.multi_table.R
import com.example.multi_table.domain.entities.MultiplicationExpression
import com.example.multi_table.presentation.theme.MainTheme


@Composable
fun AppButton(@StringRes textId: Int, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        elevation = ButtonDefaults.elevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Text(text = stringResource(textId))
    }
}

fun Modifier.buttonPadding(): Modifier = this.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)

@Composable
fun BoxScope.TimeView(seconds: Int, millis: Int) {
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

@Composable
fun ExpressionElements(
    expression: MultiplicationExpression,
    modifier: Modifier = Modifier,
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
private fun ExpressionElement(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        style = MainTheme.typographies.expressionTextStyle
    )
}