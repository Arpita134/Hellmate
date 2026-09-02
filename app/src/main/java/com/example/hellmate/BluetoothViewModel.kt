package com.example.hellmate

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID

sealed class BluetoothUiState {
    object Disconnected : BluetoothUiState()
    object Connecting : BluetoothUiState()
    data class Connected(val deviceName: String) : BluetoothUiState()
    data class Error(val message: String) : BluetoothUiState()
}

class BluetoothViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<BluetoothUiState>(BluetoothUiState.Disconnected)
    val uiState: StateFlow<BluetoothUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages.asStateFlow()

    private val deviceName = "Smart_Helmet"
    private val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var bluetoothSocket: BluetoothSocket? = null

    @SuppressLint("MissingPermission")
    fun connectToHelmet(bluetoothAdapter: BluetoothAdapter?) {
        if (bluetoothAdapter == null) {
            _uiState.value = BluetoothUiState.Error("Bluetooth not supported")
            return
        }

        viewModelScope.launch {
            _uiState.value = BluetoothUiState.Connecting

            try {
                val pairedDevices = bluetoothAdapter.bondedDevices
                val espDevice = pairedDevices.find { it.name == deviceName }

                if (espDevice == null) {
                    _uiState.value = BluetoothUiState.Error("Smart_Helmet not paired")
                    return@launch
                }

                withContext(Dispatchers.IO) {
                    bluetoothAdapter.cancelDiscovery()
                    val socket = espDevice.createRfcommSocketToServiceRecord(SPP_UUID)
                    socket.connect()
                    bluetoothSocket = socket
                }

                _uiState.value = BluetoothUiState.Connected(deviceName)
                startReceivingData()

            } catch (e: Exception) {
                _uiState.value = BluetoothUiState.Error("Connection failed: ${e.message}")
            }
        }
    }

    private fun startReceivingData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream = bluetoothSocket?.inputStream ?: return@launch
                val reader = BufferedReader(InputStreamReader(inputStream))

                while (true) {
                    val message = reader.readLine() ?: break
                    if (message.isNotBlank()) {
                        _messages.value = _messages.value + message
                    }
                }
            } catch (_: Exception) {
                _uiState.value = BluetoothUiState.Disconnected
            } finally {
                closeConnection()
            }
        }
    }

    fun closeConnection() {
        try {
            bluetoothSocket?.close()
        } catch (e: Exception) {
            // Log error
        }
        bluetoothSocket = null
        _uiState.value = BluetoothUiState.Disconnected
    }

    override fun onCleared() {
        closeConnection()
    }
}
