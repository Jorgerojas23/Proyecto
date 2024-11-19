package com.example.estudiantes.Screen

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.estudiantes.Model.Curso
import com.example.estudiantes.Model.Profesor
import com.example.estudiantes.ViewModel.CursoViewModel
import com.example.estudiantes.ViewModel.ProfesorViewModel
import com.example.estudiantes.ViewModel.CursoViewModelFactory
import com.example.estudiantes.ViewModel.ProfesorViewModelFactory
import com.example.estudiantes.Repository.CursoRepository
import com.example.estudiantes.Repository.ProfesorRepository
import androidx.compose.runtime.livedata.observeAsState


class CursoScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // Ahora los ViewModels se inicializan directamente dentro de Composable
                val cursoViewModel: CursoViewModel = viewModel(
                    factory = CursoViewModelFactory(CursoRepository(application))
                )
                val profesorViewModel: ProfesorViewModel = viewModel(
                    factory = ProfesorViewModelFactory(ProfesorRepository(application))
                )

                // Pasar los ViewModels como parámetros
                CursoScreenContent(
                    cursoViewModel = cursoViewModel,
                    profesorViewModel = profesorViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursoScreenContent(
    cursoViewModel: CursoViewModel,
    profesorViewModel: ProfesorViewModel
) {
    val context = LocalContext.current

    // Cambio a observeAsState() para LiveData de profesores
    val cursos by cursoViewModel.cursos.collectAsState(initial = emptyList())
    val profesores by profesorViewModel.allProfesores.observeAsState(initial = emptyList())

    var nombreCurso by remember { mutableStateOf("") }
    var profesorSeleccionadoId by remember { mutableStateOf("") }

    // Estado para controlar el diálogo de selección
    var mostrarDialogoProfesor by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de Cursos", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de nombre del curso
        OutlinedTextField(
            value = nombreCurso,
            onValueChange = { nombreCurso = it },
            label = { Text("Nombre del Curso") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para mostrar el diálogo de selección de profesor
        OutlinedButton(
            onClick = { mostrarDialogoProfesor = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (profesorSeleccionadoId.isEmpty()) "Seleccionar Profesor"
                else profesores.find { it.identificacion == profesorSeleccionadoId }?.let { "${it.nombre} ${it.apellido}" }
                    ?: "Seleccionar Profesor"
            )
        }

        // Diálogo de selección de profesor
        if (mostrarDialogoProfesor) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoProfesor = false },
                title = { Text("Seleccionar Profesor") },
                text = {
                    LazyColumn {
                        items(profesores) { profesor ->
                            ListItem(
                                headlineContent = {
                                    Text("${profesor.nombre} ${profesor.apellido}")
                                },
                                modifier = Modifier.clickable {
                                    profesorSeleccionadoId = profesor.identificacion
                                    mostrarDialogoProfesor = false
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { mostrarDialogoProfesor = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar curso
        Button(
            onClick = {
                if (nombreCurso.isNotEmpty() && profesorSeleccionadoId.isNotEmpty()) {
                    cursoViewModel.agregarCurso(
                        Curso(
                            nombreCurso = nombreCurso,
                            profesorId = profesorSeleccionadoId.toIntOrNull() ?: 0
                        )
                    )
                    nombreCurso = ""
                    profesorSeleccionadoId = ""
                    Toast.makeText(context, "Curso agregado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Curso")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de cursos
        Text("Lista de Cursos", style = MaterialTheme.typography.titleMedium)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cursos) { curso ->
                val profesor = profesores.find { it.identificacion == curso.profesorId.toString() }
                CursoItem(
                    curso = curso,
                    profesorNombre = profesor?.let { "${it.nombre} ${it.apellido}" } ?: "Profesor no encontrado",
                    onEliminar = { cursoViewModel.eliminarCurso(curso) }
                )
            }
        }
    }
}

@Composable
fun CursoItem(
    curso: Curso,
    profesorNombre: String,
    onEliminar: (Curso) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = curso.nombreCurso,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Profesor: $profesorNombre",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = { onEliminar(curso) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar curso")
            }
        }
    }
}
