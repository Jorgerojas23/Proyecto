package com.example.estudiantes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.estudiantes.Repository.InscripcionRepository

class InscripcionViewModelFactory(
    private val repository: InscripcionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InscripcionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InscripcionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
