package com.example.sw0b_001.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// Data class to represent a language option
data class LanguageOption(val name: String, val code: String)

@Composable
fun LanguageSelectionPopup(
    currentLanguageCode: String,
    onLanguageSelected: (LanguageOption) -> Unit,
    onDismiss: () -> Unit
) {
    val languageOptions = listOf(
        LanguageOption("English", "en"),
        LanguageOption("French", "fr"),
        LanguageOption("Farsi", "fa"),
        LanguageOption("Spanish", "es"),
        LanguageOption("Turkish", "tr")
    )

    var selectedLanguageCode by remember { mutableStateOf(currentLanguageCode) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                text = "Select Language",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.padding(8.dp))

            languageOptions.forEach { language ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedLanguageCode = language.code }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedLanguageCode == language.code,
                        onClick = { selectedLanguageCode = language.code }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = language.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = "Cancel",
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Ok",
                    modifier = Modifier
                        .clickable {
                            val selectedLanguage = languageOptions.first { it.code == selectedLanguageCode }
                            onLanguageSelected(selectedLanguage)
                            onDismiss()
                        }
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionPopupPreview() {
    LanguageSelectionPopup(
        currentLanguageCode = "en",
        onLanguageSelected = {},
        onDismiss = {}
    )
}