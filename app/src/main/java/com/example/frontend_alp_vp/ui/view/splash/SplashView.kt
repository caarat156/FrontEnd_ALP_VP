package com.example.frontend_alp_vp.ui.view.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.frontend_alp_vp.R
import kotlinx.coroutines.delay

@Composable
fun SplashView(
    onSplashFinished: () -> Unit
) {
    // 1. Timer: Wait 3 seconds, then tell Navigation to move on
    LaunchedEffect(Unit) {
        delay(3000)
        onSplashFinished()
    }

    // 2. UI: Exact same design as your Activity
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary) // Your Red 9A3F3F
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
    }
}