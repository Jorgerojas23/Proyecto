package com.example.estudiantes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.estudiantes.Model.Profesor
import com.example.estudiantes.Repository.ProfesorRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfesorViewModel(private val profesorRepository: ProfesorRepository) : ViewModel() {
    val allProfesores: LiveData<List<Profesor>> = profesorRepository.getAllProfesores()


    fun verificarIdentificacionDuplicada(identificacion: String): Boolean {
        return runBlocking {
            profesorRepository.existeIdentificacion(identificacion)
        }
    }

    fun insert(profesor: Profesor, onResult: ((Boolean) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                profesorRepository.insert(profesor)
                onResult?.invoke(true)
            } catch (e: Exception) {
                onResult?.invoke(false)
            }
        }
    }
}