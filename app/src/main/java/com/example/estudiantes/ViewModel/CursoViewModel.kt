package com.example.estudiantes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estudiantes.Model.Curso
import com.example.estudiantes.Repository.CursoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CursoViewModel(
    private val cursoRepository: CursoRepository
) : ViewModel() {

    // Flow para observar los cursos desde el repositorio
    val cursos: StateFlow<List<Curso>> = cursoRepository.obtenerCursos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Función para agregar un curso
    fun agregarCurso(curso: Curso) {
        viewModelScope.launch {
            cursoRepository.agregarCurso(curso)
        }
    }

    // Función para actualizar un curso
    fun actualizarCurso(curso: Curso) {
        viewModelScope.launch {
            cursoRepository.actualizarCurso(curso)
        }
    }

    // Función para eliminar un curso
    fun eliminarCurso(curso: Curso) {
        viewModelScope.launch {
            cursoRepository.eliminarCurso(curso)
        }
    }
}
