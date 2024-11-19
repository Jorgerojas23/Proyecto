package com.example.estudiantes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.estudiantes.Repository.EstudianteRepository

class EstudianteViewModelFactory(
    private val repository: EstudianteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstudianteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstudianteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
