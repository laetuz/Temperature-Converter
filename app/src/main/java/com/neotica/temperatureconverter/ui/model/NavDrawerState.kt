package com.neotica.temperatureconverter.ui.model

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavDrawerState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val drawerState: DrawerState,
    val scope: CoroutineScope,
    val context: Context
) {
    @OptIn(ExperimentalMaterial3Api::class)
    fun onMenuClick() {
        scope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else if (drawerState.isOpen) {
                drawerState.close()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberNavDrawerState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
    snackBarState: SnackbarHostState = remember { SnackbarHostState() }
): NavDrawerState =
    remember(
        drawerState, scope, context, snackBarState
    ) {
        NavDrawerState(drawerState, scope, context)
    }