package com.example.estudiantes.ViewModel

import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.example.estudiantes.Model.Inscripcion
import com.example.estudiantes.Repository.InscripcionRepository
import kotlinx.coroutines.launch

class InscripcionViewModel(private val repository: InscripcionRepository) : ViewModel() {
    val allInscripciones: LiveData<List<Inscripcion>> = repository.allInscripciones.asLiveData()

    fun insert(inscripcion: Inscripcion) = viewModelScope.launch {
        repository.insert(inscripcion)
    }

    fun update(inscripcion: Inscripcion) = viewModelScope.launch {
        repository.update(inscripcion)
    }

    fun delete(inscripcion: Inscripcion) = viewModelScope.launch {
        repository.delete(inscripcion)
    }
}
