package com.example.climafilm.presentation.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafilm.presentation.viewmodels.settings.AppThemeOption
import com.example.climafilm.presentation.viewmodels.settings.SettingsViewModel
import com.example.climafilm.presentation.viewmodels.settings.displayName
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val isSheetOpened by viewModel.isSheetOpened.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val hasFocus = interactionSource.collectIsFocusedAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isSheetOpened) {
        if (isSheetOpened && uiState != null) {
            delay(200)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    LaunchedEffect(hasFocus.value) {
        if (hasFocus.value && viewModel.userNameField.selection.collapsed) {
            viewModel.userNameField = viewModel.userNameField.copy(
                selection = TextRange(viewModel.userNameField.text.length)
            )
        }
    }

    if (uiState == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            DefaultItemSelector(
                title = "Nome do usuário",
                value = viewModel.userName,
                onClick = { viewModel.openSheet() }
            )

            if (isSheetOpened) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        viewModel.closeSheet()
                        keyboardController?.hide()
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = viewModel.userNameField,
                            onValueChange = { viewModel.updateUserName(it) },
                            label = { Text(text = "Nome", color = MaterialTheme.colorScheme.onBackground) },
                            singleLine = true,
                            interactionSource = interactionSource,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                        )
                    }

                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            viewModel.saveUserName()
                            viewModel.closeSheet()
                            keyboardController?.hide()
                        }
                    ) {
                        Text(text = "Salvar", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            DefaultDialogSelector(
                title = "Unidade de temperatura",
                currentSelection = uiState?.temperatureUnit ?: "Celsius",
                options = listOf("Celsius"),
                optionLabel = { it },
                onSelect = { viewModel.updateTemperatureUnit(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            DefaultDialogSelector(
                title = "Idioma",
                currentSelection = uiState?.language ?: "Português",
                options = listOf("Português"),
                optionLabel = { it },
                onSelect = { viewModel.updateLanguage(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            DefaultDialogSelector(
                title = "Tema",
                currentSelection = uiState?.theme ?: AppThemeOption.SYSTEM,
                options = AppThemeOption.entries,
                optionLabel = { it.displayName() },
                onSelect = { viewModel.updateTheme(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)

            Text(
                text = "Sobre o app",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Versão: 1.0.0",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Desenvolvido por Wagner",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DefaultItemSelector(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Edit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = value.ifBlank { "Não definido" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun <T> DefaultDialogSelector(
    modifier: Modifier = Modifier,
    title: String,
    currentSelection: T,
    options: List<T>,
    optionLabel: (T) -> String,
    onSelect: (T) -> Unit,
    icon: ImageVector = Icons.Default.ArrowDropDown
) {
    var dialogOpen by remember { mutableStateOf(false) }

    DefaultItemSelector(
        title = title,
        value = optionLabel(currentSelection),
        icon = icon,
        onClick = { dialogOpen = true }
    )

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = { dialogOpen = false },
            title = {
                Text(title, style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Column {
                    options.forEach { option ->
                        val isSelected = option == currentSelection
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(option)
                                    dialogOpen = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Filled.RadioButtonChecked else Icons.Filled.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(optionLabel(option))
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}