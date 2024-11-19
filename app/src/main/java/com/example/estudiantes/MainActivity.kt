package com.example.estudiantes



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.estudiantes.Screen.CursoScreenContent
import com.example.estudiantes.Screen.EstudianteScreen
import com.example.estudiantes.Screen.MisionScreen
import com.example.estudiantes.Screen.PrincipalScreen
import com.example.estudiantes.Screen.ProfesorScreen
import com.example.estudiantes.Repository.CursoRepository
import com.example.estudiantes.Repository.EstudianteRepository
import com.example.estudiantes.Repository.ProfesorRepository
import com.example.estudiantes.ViewModel.CursoViewModel
import com.example.estudiantes.ViewModel.CursoViewModelFactory
import com.example.estudiantes.ViewModel.EstudianteViewModel
import com.example.estudiantes.ViewModel.EstudianteViewModelFactory
import com.example.estudiantes.ViewModel.ProfesorViewModel
import com.example.estudiantes.ViewModel.ProfesorViewModelFactory
import com.example.estudiantes.ui.theme.EstudiantesTheme
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstudiantesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Crear los Repositories y ViewModelFactories
                    val estudianteRepository = EstudianteRepository(application)
                    val estudianteViewModelFactory = EstudianteViewModelFactory(estudianteRepository)

                    val profesorRepository = ProfesorRepository(application)
                    val profesorViewModelFactory = ProfesorViewModelFactory(profesorRepository)

                    val cursoRepository = CursoRepository(application)
                    val cursoViewModelFactory = CursoViewModelFactory(cursoRepository)

                    NavHost(
                        navController = navController,
                        startDestination = "principal_screen"
                    ) {
                        composable("principal_screen") {
                            PrincipalScreen(navController)
                        }
                        composable("estudiante_screen") {
                            val estudianteViewModel = ViewModelProvider(
                                this@MainActivity,
                                estudianteViewModelFactory
                            ).get(EstudianteViewModel::class.java)

                            EstudianteScreen(navController = navController, estudianteViewModel = estudianteViewModel)
                        }
                        composable("profesor_screen") {
                            val profesorViewModel = ViewModelProvider(
                                this@MainActivity,
                                profesorViewModelFactory
                            ).get(ProfesorViewModel::class.java)

                            ProfesorScreen(
                                profesorViewModel = profesorViewModel,
                                onProfesorSelected = { profesor -> /* Acciones al seleccionar un profesor */ }
                            )
                        }
                        composable("curso_screen") {
                            val cursoViewModel = ViewModelProvider(
                                this@MainActivity,
                                cursoViewModelFactory
                            ).get(CursoViewModel::class.java)

                            val profesorViewModel = ViewModelProvider(
                                this@MainActivity,
                                profesorViewModelFactory
                            ).get(ProfesorViewModel::class.java)

                            CursoScreenContent(
                                cursoViewModel = cursoViewModel,
                                profesorViewModel = profesorViewModel
                            )
                        }
                        composable("mision_screen") {
                            MisionScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
