package com.example.hellmate.screens

import android.bluetooth.BluetoothAdapter
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
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ContactEmergency
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Emergency
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hellmate.BluetoothUiState
import com.example.hellmate.BluetoothViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: BluetoothViewModel = BluetoothViewModel(),
    adapter: BluetoothAdapter? = null,
    onSos: () -> Unit = {},
    onEditEmergencies: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()

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
                Column (
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .size(120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (uiState) {
                        is BluetoothUiState.Connected -> {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .aspectRatio(1f)
                                    .padding(32.dp),
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }

                        is BluetoothUiState.Disconnected -> {
                            IconButton(
                                onClick = { viewModel.connectToHelmet(bluetoothAdapter = adapter) },
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(120.dp).padding(32.dp)
                                )
                            }
                        }

                        is BluetoothUiState.Connecting -> {
                            LoadingIndicator(
                                modifier = Modifier
                                    .size(120.dp).padding(32.dp)
                            )
                        }

                        is BluetoothUiState.Error -> {
                            IconButton(
                                onClick = { viewModel.connectToHelmet(bluetoothAdapter = adapter) },
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Warning,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(120.dp).padding(32.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                when (uiState) {
                    is BluetoothUiState.Connected -> {
                        Text(
                            text = "System active",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Crash detection is ON",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is BluetoothUiState.Disconnected -> {
                        Text(
                            text = "System inactive",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Crash detection is OFF",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is BluetoothUiState.Connecting -> {
                        Text(
                            text = "Connecting to system",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Keep device connected via bluetooth",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is BluetoothUiState.Error -> {
                        Text(
                            text = "Failed to connect",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Checked bluetooth?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
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
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Last location",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Text(
                        text = "Somewhere near Golden Jubilee building...",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.BatteryStd,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Battery",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Text(
                        text = "36.46215%",
                        style = MaterialTheme.typography.bodySmall
                    )
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
