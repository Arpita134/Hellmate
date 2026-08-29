package com.example.hellmate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BatteryStd
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ContactEmergency
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Emergency
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
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

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSos: () -> Unit = {},
    onEditEmergencies: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        .size(120.dp)
                        .aspectRatio(1f)
                        .padding(32.dp),
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("System active")
                Text("Crash detection is ON")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null)
                        Text("Last location")
                    }
                    Text("Somewhere near Golden Jubilee building...")
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.BatteryStd, contentDescription = null)
                        Text("Battery")
                    }
                    Text("36.46215%")
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = onSos,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Emergency, contentDescription = null)
                    Text("Send SOS Now")
                }
            }
            Button(
                onClick = onEditEmergencies,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Contacts, contentDescription = null)
                    Text("Edit emergency contacts")
                }
            }
        }
    }
}
