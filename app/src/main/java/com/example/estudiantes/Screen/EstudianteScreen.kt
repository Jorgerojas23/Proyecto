package com.example.estudiantes.Screen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.estudiantes.Model.Estudiante
import com.example.estudiantes.ViewModel.EstudianteViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteScreen(
    navController: NavController,
    estudianteViewModel: EstudianteViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var numeroIdentificacion by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf<Date?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isFormSubmitted by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Validación de los campos
    val isNombreValido = remember(nombre) {
        nombre.trim().length in 2..50 &&
                nombre.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$"))
    }
    val isApellidoValido = remember(apellido) {
        apellido.trim().length in 2..50 &&
                apellido.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$"))
    }
    val isIdentificacionValida = remember(numeroIdentificacion) {
        numeroIdentificacion.matches(Regex("^\\d{6,15}$"))
    }
    val isFechaNacimientoValida = remember(fechaNacimiento) {
        fechaNacimiento?.let {
            val edad = calcularEdad(it)
            edad in 4..12
        } ?: false
    }

    val isFormularioValido = isNombreValido &&
            isApellidoValido &&
            isIdentificacionValida &&
            isFechaNacimientoValida

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inscripción Estudiante") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                isError = isFormSubmitted && !isNombreValido,
                supportingText = {
                    if (isFormSubmitted && !isNombreValido) {
                        Text(
                            text = "Nombre inválido (2-50 caracteres, solo letras)",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isFormSubmitted && !isNombreValido) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Apellido
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                isError = isFormSubmitted && !isApellidoValido,
                supportingText = {
                    if (isFormSubmitted && !isApellidoValido) {
                        Text(
                            text = "Apellido inválido (2-50 caracteres, solo letras)",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isFormSubmitted && !isApellidoValido) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Número de identificación
            OutlinedTextField(
                value = numeroIdentificacion,
                onValueChange = { numeroIdentificacion = it },
                label = { Text("Número de Identificación") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isFormSubmitted && !isIdentificacionValida,
                supportingText = {
                    if (isFormSubmitted && !isIdentificacionValida) {
                        Text(
                            text = "Identificación inválida (6-15 dígitos)",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (isFormSubmitted && !isIdentificacionValida) {
                        Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Fecha de nacimiento
            FechaNacimientoPicker(fechaNacimiento = fechaNacimiento) { fecha ->
                fechaNacimiento = fecha
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar el formulario
            Button(
                onClick = {
                    isFormSubmitted = true
                    if (isFormularioValido) {
                        val identificacionInt = numeroIdentificacion.toInt()
                        if (estudianteViewModel.verificarIdentificacionDuplicada(identificacionInt)) {
                            errorMessage = "Este número de identificación ya está registrado."
                            showErrorDialog = true
                        } else {
                            showConfirmationDialog = true
                        }
                    }
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Realizar Inscripción")
            }
        }

        // Diálogo de confirmación
        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Confirmar Inscripción") },
                text = {
                    Column {
                        Text("Por favor, revise los siguientes datos:")
                        Text("Nombre: $nombre")
                        Text("Apellido: $apellido")
                        Text("Identificación: $numeroIdentificacion")
                        Text("Fecha Nacimiento: ${formatDate(fechaNacimiento!!)}")
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val estudiante = Estudiante(
                                nombre = nombre.trim(),
                                apellido = apellido.trim(),
                                identificacion = numeroIdentificacion.toInt(),
                                fecha_nacimiento = fechaNacimiento!!
                            )
                            estudianteViewModel.insert(estudiante)
                            showConfirmationDialog = false
                            showSuccessDialog = true
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showConfirmationDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo de éxito
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("¡Inscripción Exitosa!") },
                text = {
                    Text("El estudiante ha sido inscrito correctamente en el sistema.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Resetear el formulario
                            nombre = ""
                            apellido = ""
                            numeroIdentificacion = ""
                            fechaNacimiento = null
                            isFormSubmitted = false
                            showSuccessDialog = false

                            // Mostrar Toast
                            Toast.makeText(
                                context,
                                "Inscripción realizada con éxito",
                                Toast.LENGTH_LONG
                            ).show()

                            // Navegar a la pantalla principal
                            navController.navigate("principal_screen")
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }

        // Diálogo de error
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text(errorMessage) },
                confirmButton = {
                    TextButton(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

// Función para formatear la fecha a String
fun formatDate(date: Date): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
}

// Componente de selección de fecha
@Composable
fun FechaNacimientoPicker(fechaNacimiento: Date?, onFechaSeleccionada: (Date) -> Unit) {
    val context = LocalContext.current
    OutlinedTextField(
        value = fechaNacimiento?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
        } ?: "",
        onValueChange = {},
        label = { Text("Fecha de Nacimiento") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Fecha de Nacimiento",
                modifier = Modifier.clickable {
                    mostrarSelectorFecha(context) { fecha ->
                        onFechaSeleccionada(fecha)
                    }
                }
            )
        },
        readOnly = true,
        modifier = Modifier.fillMaxWidth()
    )
}

// Mostrar el selector de fecha
fun mostrarSelectorFecha(context: Context, onDateSelected: (Date) -> Unit) {
    val calendario = Calendar.getInstance()
    val dia = calendario.get(Calendar.DAY_OF_MONTH)
    val mes = calendario.get(Calendar.MONTH)
    val anio = calendario.get(Calendar.YEAR)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            val fechaSeleccionada = Calendar.getInstance()
            fechaSeleccionada.set(year, monthOfYear, dayOfMonth)
            onDateSelected(fechaSeleccionada.time)
        },
        anio, mes, dia
    )

    datePickerDialog.show()
}

// Función para calcular la edad
fun calcularEdad(fechaNacimiento: Date): Int {
    val calendar = Calendar.getInstance()
    val fechaActual = Calendar.getInstance()
    fechaActual.time = Date()

    val edad = fechaActual.get(Calendar.YEAR) - fechaNacimiento.year - 1900
    val mesActual = fechaActual.get(Calendar.MONTH)
    val mesNacimiento = fechaNacimiento.month

    if (mesActual < mesNacimiento || (mesActual == mesNacimiento && fechaActual.get(Calendar.DAY_OF_MONTH) < fechaNacimiento.date)) {
        return edad - 1
    }

    return edad
}