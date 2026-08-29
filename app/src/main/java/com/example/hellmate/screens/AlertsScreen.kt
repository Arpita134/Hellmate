package com.example.hellmate.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class AlertSeverity {
    INFO,
    WARNING,
    CRASH
}

@Preview
@Composable
fun AlertsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.large),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AlertsCard("Crash detected", "Today, 19:45", AlertSeverity.CRASH)
        AlertsCard("Sleepiness detected", "Today, 19:32", AlertSeverity.WARNING)
        AlertsCard("Ride started", "Today, 19:28", AlertSeverity.INFO)
    }
}

@Composable
fun AlertsCard(title: String, timeStamp: String?, severity: AlertSeverity) {
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (severity) {
                        AlertSeverity.INFO -> Icons.Outlined.PushPin
                        AlertSeverity.WARNING -> Icons.Outlined.Info
                        AlertSeverity.CRASH -> Icons.Outlined.Warning
                    },
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(title)
                    if (timeStamp != null) Text(timeStamp)
                }
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,

            )
        }
    }
}