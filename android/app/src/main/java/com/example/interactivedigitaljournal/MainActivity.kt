package com.example.interactivedigitaljournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interactivedigitaljournal.auth.domain.repository.AuthResponse
import com.example.interactivedigitaljournal.common.presentation.navigation.ScreenHome
import com.example.interactivedigitaljournal.auth.presentation.navigation.ScreenSignIn
import com.example.interactivedigitaljournal.ui.theme.InteractiveDigitalJournalTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.interactivedigitaljournal.auth.presentation.navigation.ScreenSignUp
import com.example.interactivedigitaljournal.auth.presentation.screen.SignInScreen
import com.example.interactivedigitaljournal.auth.presentation.screen.SignUpScreen
import com.example.interactivedigitaljournal.auth.presentation.view_model.AuthViewModel
import com.example.interactivedigitaljournal.common.presentation.screen.HomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InteractiveDigitalJournalTheme(darkTheme = false) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val authState = viewModel.uiState.collectAsState().value

    // Determine start destination based on authentication state
    val startDestination = when(authState.isAuthorizedResponse) {
        is AuthResponse.Success -> ScreenHome
        else -> ScreenSignUp
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<ScreenSignUp> {
            SignUpScreen(
                onRegister = {
                    navController.navigate(ScreenSignIn) {
                        popUpTo(ScreenSignUp) { inclusive = true }
                    }
                }
            )
        }
        composable<ScreenSignIn> {
            SignInScreen(
                onSignIn = {
                    navController.navigate(ScreenHome) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }
        composable<ScreenHome> {
            HomeScreen()
        }
    }
}