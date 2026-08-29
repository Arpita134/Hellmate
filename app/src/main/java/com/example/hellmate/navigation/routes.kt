package com.example.hellmate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Emergency
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes(
    val route: String,
    val title: String,
    val hasBack: Boolean = false,
    val hasNavigation: Boolean = false,
    val icon: ImageVector? = null
) {
    Home("home", "HellMate", hasNavigation = true, icon = Icons.Outlined.Home),
    EmergencyContacts("econtacts", "Emergency Contacts", true),
    Alerts("alerts", "Alert History", hasNavigation = true, icon = Icons.Outlined.AddAlert),
    Settings("settings", "Settings", hasNavigation = true, icon = Icons.Outlined.Settings),
    Sos("sos", "Send SOS", true)
}