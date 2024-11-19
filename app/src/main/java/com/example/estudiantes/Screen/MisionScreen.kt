package com.example.estudiantes.Screen

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.estudiantes.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisionScreen(navController: NavController) {
    var showMision by remember { mutableStateOf(false) }
    var showVision by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        showMision = true
        delay(1000)
        showVision = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Elimina la parte de la imagen de fondo
        // Image(
        //     painter = painterResource(id = R.drawable.background_kids),
        //     contentDescription = "Fondo",
        //     modifier = Modifier.fillMaxSize(),
        //     contentScale = ContentScale.Crop
        // )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = showMision,
                enter = fadeIn() + expandVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nuestra Misión",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "En RefuerzaKid, nuestra misión es brindar un apoyo académico personalizado y accesible que inspire a los niños a aprender de manera divertida y efectiva.",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = showVision,
                enter = fadeIn() + expandVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nuestra Visión",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ser el centro de refuerzo académico líder en el desarrollo integral de los niños, reconocido por nuestra cercanía, metodología innovadora y enfoque lúdico.",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Button(
                onClick = { navController.navigate("principal_screen") },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .animateContentSize()
            ) {
                Text("Regresar al menú principal")
            }
        }
    }
}
