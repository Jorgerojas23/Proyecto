package com.example.estudiantes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.estudiantes.Repository.CalificacionRepository

class CalificacionViewModelFactory(
    private val repository: CalificacionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalificacionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalificacionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
