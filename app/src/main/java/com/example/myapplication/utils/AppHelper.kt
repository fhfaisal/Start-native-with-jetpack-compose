package com.example.myapplication.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object AppHelper {
    @Composable
    fun HeightBetweenItem(height: Dp? = null) {
        Spacer(modifier = Modifier.height(height ?: 20.dp))
    }

    @Composable
    fun WidthBetweenItem(width: Dp? = null) {
        Spacer(modifier = Modifier.height(width ?: 20.dp))
    }
}