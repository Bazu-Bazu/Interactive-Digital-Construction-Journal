package com.example.interactivedigitaljournal.common.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.interactivedigitaljournal.auth.presentation.screen.ProfileScreen
import com.example.interactivedigitaljournal.construction_objects.presentation.navigation.ScreenAddObject
import com.example.interactivedigitaljournal.construction_objects.presentation.screen.AddObjectScreen
import com.example.interactivedigitaljournal.construction_objects.presentation.screen.JournalScreen
import kotlinx.serialization.Serializable


@Serializable
sealed class Destination {
    @Serializable
    object Profile : Destination()

    @Serializable
    object Journal : Destination()
}

val Destination.icon: ImageVector
    get() = when (this) {
        is Destination.Journal -> Icons.Default.Home
        is Destination.Profile -> Icons.Default.Person
    }

val Destination.description: String
    get() = when (this) {
        is Destination.Journal -> "Journal"
        is Destination.Profile -> "Profile"
    }

val Destination.route: String
    get() = when (this) {
        is Destination.Journal -> "journal"
        is Destination.Profile -> "profile"
    }

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Get current destination
    val currentDestination = navBackStackEntry?.destination?.route?.let { route ->
        when (route) {
            Destination.Journal.route -> Destination.Journal
            Destination.Profile.route -> Destination.Profile
            else -> Destination.Journal // default
        }
    } ?: Destination.Journal

    val startDestination = Destination.Journal
    var selectedDestination by rememberSaveable {
        mutableIntStateOf(
            when (currentDestination) {
                is Destination.Journal -> 0
                is Destination.Profile -> 1
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                listOf(Destination.Journal, Destination.Profile).forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.description,
                            )
                        },
                        label = { Text(destination.route) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Journal,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Destination.Journal> {
                JournalScreen(
                    { navController.navigate(ScreenAddObject) }
                )
            }
            composable<Destination.Profile> { ProfileScreen() }
            composable<ScreenAddObject> { AddObjectScreen() }
        }
    }
}