package com.example.estudiantes.ViewModel

import androidx.lifecycle.*
import com.example.estudiantes.Model.Estudiante
import com.example.estudiantes.Repository.EstudianteRepository
import kotlinx.coroutines.launch


class EstudianteViewModel(private val estudianteRepository: EstudianteRepository) : ViewModel() {
    private val _allEstudiantes: LiveData<List<Estudiante>> = estudianteRepository.getAllEstudiantes()
    val allEstudiantes: LiveData<List<Estudiante>> get() = _allEstudiantes

    // Verificar si la identificación ya está registrada
    fun verificarIdentificacionDuplicada(identificacion: Int): Boolean {
        val estudiantes = allEstudiantes.value ?: emptyList() // Obtener los estudiantes actuales
        return estudiantes.any { it.identificacion == identificacion } // Buscar si la identificación ya existe
    }

    // Método para insertar un estudiante
    fun insert(estudiante: Estudiante) {
        viewModelScope.launch {
            estudianteRepository.insert(estudiante) // Llamar al método suspendido en un hilo de fondo
        }
    }
}
