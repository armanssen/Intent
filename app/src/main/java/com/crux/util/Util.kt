package com.crux.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

@Composable
@NonRestartableComposable
inline fun LaunchAndRepeatWithLifecycle(
    vararg keys: Any?,
    activeState: Lifecycle.State = Lifecycle.State.RESUMED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(*keys, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(activeState) {
            block()
        }
    }
}
