package com.example.hellmate.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SettingsScreen() {
    var autoStart by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.large),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SettingsCard("Device Status", null) {
            Text("Connected")
        }
        SettingsCard("Auto Start", "Start app on device boot") {
            Switch(
                checked = autoStart,
                onCheckedChange = { it ->
                    autoStart = it
                }
            )
        }
        SettingsCard("Permissions", "Location, SMS, Phone") {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
        }
        SettingsCard("About", "HellMate v0.01") {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
        }
    }
}

@Composable
fun SettingsCard(title: String, subtitle: String?, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(title)
                if (subtitle != null) Text(subtitle)
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }
}