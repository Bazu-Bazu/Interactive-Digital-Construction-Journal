package com.example.interactivedigitaljournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interactivedigitaljournal.auth.presentation.navigation.ScreenSignIn
import com.example.interactivedigitaljournal.ui.theme.InteractiveDigitalJournalTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.interactivedigitaljournal.auth.presentation.navigation.ScreenSignUp
import com.example.interactivedigitaljournal.auth.presentation.screen.SignInScreen
import com.example.interactivedigitaljournal.auth.presentation.screen.SignUpScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InteractiveDigitalJournalTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenSignUp
                ) {
                    composable<ScreenSignUp> {
                        SignUpScreen(
                            onRegister = {
                                navController.navigate(
                                    ScreenSignIn
                                )
                            }
                        )
                    }
                    composable<ScreenSignIn> {
                        SignInScreen()
                    }
                }
            }
        }
    }
}
