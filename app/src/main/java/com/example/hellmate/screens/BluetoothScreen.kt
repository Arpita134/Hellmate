package com.example.hellmate.screens

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hellmate.BluetoothUiState
import com.example.hellmate.BluetoothViewModel

@Composable
fun BluetoothScreen(
    viewModel: BluetoothViewModel,
    bluetoothAdapter: BluetoothAdapter?,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StatusSection(uiState)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.connectToHelmet(bluetoothAdapter) },
            enabled = uiState !is BluetoothUiState.Connecting && uiState !is BluetoothUiState.Connected
        ) {
            Text(
                text = when (uiState) {
                    is BluetoothUiState.Connected -> "Connected"
                    is BluetoothUiState.Connecting -> "Connecting..."
                    else -> "Connect to Helmet"
                }
            )
        }

        if (messages.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Recent Alerts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            ) {
                items(messages.reversed()) { message ->
                    Text(
                        text = message,
                        modifier = Modifier.padding(vertical = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun StatusSection(uiState: BluetoothUiState) {
    val statusText = when (uiState) {
        is BluetoothUiState.Disconnected -> "Status: Disconnected"
        is BluetoothUiState.Connecting -> "Status: Connecting to Smart_Helmet..."
        is BluetoothUiState.Connected -> "Status: ✓ Smart Helmet Connected"
        is BluetoothUiState.Error -> "Status: ${uiState.message}"
    }

    val statusColor = when (uiState) {
        is BluetoothUiState.Connected -> Color(0xFF4CAF50)
        is BluetoothUiState.Error -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = statusText,
        color = statusColor,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium
    )
}
