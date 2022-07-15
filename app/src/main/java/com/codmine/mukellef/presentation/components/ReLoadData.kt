package com.codmine.mukellef.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing

@Composable
fun ReLoadData(
    modifier: Modifier,
    errorMsg: UiText,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMsg.asString(),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Button(
            onClick =  onRetry,
        ) {
            Text(
                text = UiText.StringResources(resId = R.string.retry).asString()
            )
        }
    }
}