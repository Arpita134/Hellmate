package com.example.hellmate

import android.Manifest
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hellmate.navigation.NavigationBase
import com.example.hellmate.screens.BluetoothScreen
import com.example.hellmate.ui.theme.HellmateExpressiveTheme
import com.example.hellmate.ui.theme.HellmateTheme

class MainActivity : ComponentActivity() {

    private val viewModel: BluetoothViewModel by viewModels()
    private val notificationChannelId = "ACCIDENT_ALERT"

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel()
        requestPermissions()

        setContent {
            HellmateExpressiveTheme {
                val messages by viewModel.messages.collectAsState()

                // Observe messages for alerts
                LaunchedEffect(messages.size) {
                    if (messages.isNotEmpty()) {
                        val lastMessage = messages.last()
                        showAccidentNotification("Crash alert! $lastMessage")
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    BluetoothScreen(
//                        viewModel = viewModel,
//                        bluetoothAdapter = bluetoothAdapter,
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        NavigationBase(viewModel, bluetoothAdapter)
                    }
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.POST_NOTIFICATIONS
        )
        ActivityCompat.requestPermissions(this, permissions, 100)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            notificationChannelId,
            "Emergency Alerts",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Smart Helmet accident and emergency alerts"
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            enableLights(true)
            lightColor = Color.RED
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun showAccidentNotification(message: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = Notification.Builder(applicationContext, notificationChannelId)
            .setColor(Color.GREEN)
            .setColorized(true)
            .setContentTitle("Accident Alert")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_delete)
            .setStyle(Notification.DecoratedCustomViewStyle())
            .build()


        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(System.currentTimeMillis().hashCode(), notification)
    }
}
