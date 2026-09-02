package com.example.hellmate.navigation

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hellmate.BluetoothViewModel
import com.example.hellmate.screens.AlertsScreen
import com.example.hellmate.screens.EmergencyContactsScreen
import com.example.hellmate.screens.HomeScreen
import com.example.hellmate.screens.SettingsScreen
import com.example.hellmate.screens.SosScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBase(
    viewModel: BluetoothViewModel,
    adapter: BluetoothAdapter?
) {
    val navController = rememberNavController()
    var currentDestination = Routes.Home
    var destinationIndex by rememberSaveable { mutableIntStateOf(currentDestination.ordinal) }
    Scaffold(
        topBar = {
            Routes.entries.forEachIndexed { index, route ->
                if (index == destinationIndex) {
                    currentDestination = route
                }
            }
            TopAppBar(
                title = { Text(currentDestination.title) },
                navigationIcon = {
                    if (currentDestination.hasBack) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Routes.entries.forEachIndexed { index, routes ->
                    if (routes.hasNavigation) {
                        NavigationBarItem(
                            selected = destinationIndex == index,
                            onClick = {
                                navController.navigate(route = routes.route)
                                currentDestination = routes
                                destinationIndex = index
                            },
                            icon = {
                                Icon(
                                    imageVector = routes.icon ?: Icons.Outlined.Check,
                                    contentDescription = null
                                )
                            },
                            label = { Text(routes.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            NavHost(
                navController,
                startDestination = currentDestination.route
            ) {
                Routes.entries.forEach { route ->
                    composable(route.route) {
                        when (route) {
                            Routes.Home -> HomeScreen(
                                onSos = { navController.navigate(Routes.Sos.route) },
                                onEditEmergencies = { navController.navigate(Routes.EmergencyContacts.route) },
                                adapter = adapter,
                                viewModel = viewModel
                            )
                            Routes.Settings -> SettingsScreen()
                            Routes.EmergencyContacts -> EmergencyContactsScreen()
                            Routes.Alerts -> AlertsScreen()
                            Routes.Sos -> SosScreen()
                        }
                    }
                }
            }
        }
    }
}