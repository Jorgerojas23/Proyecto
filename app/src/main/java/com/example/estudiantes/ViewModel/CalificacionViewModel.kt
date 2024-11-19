package com.example.estudiantes.ViewModel

import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.example.estudiantes.Model.Calificacion
import com.example.estudiantes.Repository.CalificacionRepository
import kotlinx.coroutines.launch

class CalificacionViewModel(private val repository: CalificacionRepository) : ViewModel() {
    val allCalificaciones: LiveData<List<Calificacion>> = repository.allCalificaciones.asLiveData()

    fun insert(calificacion: Calificacion) = viewModelScope.launch {
        repository.insert(calificacion)
    }

    fun update(calificacion: Calificacion) = viewModelScope.launch {
        repository.update(calificacion)
    }

    fun delete(calificacion: Calificacion) = viewModelScope.launch {
        repository.delete(calificacion)
    }
}
