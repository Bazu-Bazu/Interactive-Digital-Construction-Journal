package com.example.interactivedigitaljournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.interactivedigitaljournal.auth.presentation.screen.SignUpScreen
import com.example.interactivedigitaljournal.ui.theme.InteractiveDigitalJournalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InteractiveDigitalJournalTheme {
                SignUpScreen()
            }
        }
    }
}
