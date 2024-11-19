package com.example.estudiantes.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(navController: NavController) {
    var elementoSeleccionado by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "RefuerzaKid",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Página Principal") },
                    label = { Text("Inicio") },
                    selected = elementoSeleccionado == 0,
                    onClick = { elementoSeleccionado = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Ir a Estudiantes") },
                    label = { Text("Estudiantes") },
                    selected = elementoSeleccionado == 1,
                    onClick = {
                        elementoSeleccionado = 1
                        navController.navigate("estudiante_screen")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Book, contentDescription = "Ir a Cursos") },
                    label = { Text("Cursos") },
                    selected = elementoSeleccionado == 2,
                    onClick = {
                        elementoSeleccionado = 2
                        navController.navigate("curso_screen")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.People, contentDescription = "Ir a Profesores") },
                    label = { Text("Profesores") },
                    selected = elementoSeleccionado == 3,
                    onClick = {
                        elementoSeleccionado = 3
                        navController.navigate("profesor_screen")
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Sección Principal (Hero)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Transformamos el potencial en éxito",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Apoyo académico cercano, accesible y divertido para niños",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            // Sección de Características
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "¿Por qué elegir RefuerzaKid?",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TarjetaCaracteristica(
                        icon = Icons.Filled.Star,
                        titulo = "Aprendizaje Divertido",
                        descripcion = "Metodologías interactivas y dinámicas"
                    )
                    TarjetaCaracteristica(
                        icon = Icons.Filled.Lightbulb,
                        titulo = "Atención Personalizada",
                        descripcion = "Adaptado al ritmo de cada estudiante"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TarjetaCaracteristica(
                        icon = Icons.Filled.CheckCircle,
                        titulo = "Profesores Expertos",
                        descripcion = "Equipo docente calificado"
                    )
                    TarjetaCaracteristica(
                        icon = Icons.Filled.Favorite,
                        titulo = "Resultados Garantizados",
                        descripcion = "Mejora académica demostrable"
                    )
                }
            }

            // Sección de Opciones Principales
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BotonPrincipal(
                    texto = "¿Deseas inscribirte? ¡Hazlo aquí!",
                    icon = Icons.Filled.PersonAdd,
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = { navController.navigate("estudiante_screen") }
                )

                BotonPrincipal(
                    texto = "Mira los cursos disponibles",
                    icon = Icons.Filled.MenuBook,
                    color = MaterialTheme.colorScheme.primary,
                    onClick = { navController.navigate("curso_screen") }
                )

                BotonPrincipal(
                    texto = "Inscripción Profesores",
                    icon = Icons.Filled.Assignment,
                    color = MaterialTheme.colorScheme.tertiary,
                    onClick = { navController.navigate("profesor_screen") }
                )

                BotonPrincipal(
                    texto = "Conoce nuestra misión y visión",
                    icon = Icons.Filled.Info,
                    color = MaterialTheme.colorScheme.error,
                    onClick = { navController.navigate("mision_screen") }
                )
            }



            // Pie de página
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "RefuerzaKid",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Transformamos el potencial en éxito",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "© 2024 RefuerzaKid. Todos los derechos reservados.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun BotonPrincipal(
    texto: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(texto, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TarjetaCaracteristica(
    icon: ImageVector,
    titulo: String,
    descripcion: String
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
