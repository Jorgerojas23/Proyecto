package com.example.estudiantes.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.estudiantes.DAO.ProfesorDao
import com.example.estudiantes.Database.EstudiantesDatabase
import com.example.estudiantes.Model.Profesor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfesorRepository(private val application: Application) {
    private val profesorDao: ProfesorDao = EstudiantesDatabase.getDatabase(application).profesorDao()

    // Verificar si una identificación existe en la base de datos
    suspend fun existeIdentificacion(identificacion: String): Boolean {
        return profesorDao.existeIdentificacion(identificacion)
    }
    // Obtener todos los profesores
    fun getAllProfesores(): LiveData<List<Profesor>> {
        return profesorDao.getAllProfesores()
    }


    // Insertar un profesor en un hilo de fondo
    suspend fun insert(profesor: Profesor) {
        withContext(Dispatchers.IO) {
            profesorDao.insert(profesor)
        }
    }
}
