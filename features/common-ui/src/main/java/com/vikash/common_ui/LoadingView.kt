package com.vikash.common_ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    sizeDp: Int = 48,
    color: androidx.compose.ui.graphics.Color? = null
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(sizeDp.dp),
            color = color ?: androidx.compose.material3.MaterialTheme.colorScheme.primary
        )
    }
}