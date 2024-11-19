package com.example.estudiantes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.estudiantes.Repository.ProfesorRepository

class ProfesorViewModelFactory(
    private val repository: ProfesorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfesorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfesorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
