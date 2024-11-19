package com.example.estudiantes.Screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.estudiantes.Model.Profesor
import com.example.estudiantes.ViewModel.ProfesorViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfesorScreen(
    profesorViewModel: ProfesorViewModel,
    onProfesorSelected: (Profesor) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var numeroIdentificacion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var showConfirmationTable by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf("") }

    val isNombreValido = remember(nombre) { nombre.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$")) }
    val isApellidoValido = remember(apellido) { apellido.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$")) }
    val isIdentificacionValida = remember(numeroIdentificacion) { numeroIdentificacion.matches(Regex("^\\d{1,15}$")) }
    val isTelefonoValido = remember(telefono) { telefono.matches(Regex("^\\d{7,15}$")) }
    val isFormularioValido = isNombreValido && isApellidoValido && isIdentificacionValida && isTelefonoValido

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandVertically()
        ) {
            Text(
                text = "Registro de Profesor",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del profesor") },
                    isError = !isNombreValido && nombre.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre") }
                )

                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido del profesor") },
                    isError = !isApellidoValido && apellido.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Apellido") }
                )

                OutlinedTextField(
                    value = numeroIdentificacion,
                    onValueChange = { numeroIdentificacion = it },
                    label = { Text("Número de Identificación") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = !isIdentificacionValida && numeroIdentificacion.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = "Identificación") }
                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = !isTelefonoValido && telefono.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Teléfono") }
                )
            }
        }

        Button(
            onClick = {
                if (isFormularioValido) {
                    val duplicado = profesorViewModel.verificarIdentificacionDuplicada(numeroIdentificacion)
                    if (duplicado) {
                        showErrorMessage = "Este número de identificación ya está registrado."
                    } else {
                        showConfirmationTable = true
                        showErrorMessage = ""
                    }
                }
            },
            enabled = isFormularioValido,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Registrar")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar Profesor")
        }

        AnimatedVisibility(
            visible = showConfirmationTable,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Verifique los datos antes de completar el registro",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider()
                    Text("Nombre: $nombre")
                    Text("Apellido: $apellido")
                    Text("Número de Identificación: $numeroIdentificacion")
                    Text("Teléfono: $telefono")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val profesor = Profesor(
                                    nombre = nombre.trim(),
                                    apellido = apellido.trim(),
                                    identificacion = numeroIdentificacion.trim(),
                                    telefono = telefono.trim(),
                                )
                                profesorViewModel.insert(profesor) { success ->
                                    if (success) {
                                        nombre = ""
                                        apellido = ""
                                        numeroIdentificacion = ""
                                        telefono = ""
                                        showConfirmationTable = false
                                        showSuccessMessage = true
                                    } else {
                                        showErrorMessage = "Error al registrar el profesor"
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Confirmar")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Confirmar Registro")
                        }
                        Button(
                            onClick = { showConfirmationTable = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Editar Datos")
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showSuccessMessage,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Éxito", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "¡Registro completado exitosamente!",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showErrorMessage.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = showErrorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}