package com.maju.lib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.maju.lib.navigation.AppNavigation
import com.maju.lib.ui.theme.LibTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibTheme {
                AppNavigation()
            }
        }
    }
}