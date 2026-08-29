package com.example.hellmate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Contact(val name: String, val number: String)

@Preview
@Composable
fun EmergencyContactsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val SAMPLE_CONTACTS = remember {
            listOf(
                Contact("Someone", "7655092713"),
                Contact("Somebody", "1253536664"),
                Contact("Pravat", "Nevermind")
            )
        }
        for (contact in SAMPLE_CONTACTS) {
            ContactCard(contact)
        }
    }
}

@Composable
fun ContactCard(contact: Contact) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.EditNote, contentDescription = null)
                    Text(contact.name)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Call, contentDescription = null)
                    Text(contact.number)
                }
            }
        }
    }
}