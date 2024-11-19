package com.example.estudiantes.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.estudiantes.DAO.EstudianteDao
import com.example.estudiantes.Database.EstudiantesDatabase
import com.example.estudiantes.Model.Estudiante
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EstudianteRepository(private val application: Application) {
    private val estudianteDao: EstudianteDao = EstudiantesDatabase.getDatabase(application).estudianteDao()

    // Obtener todos los estudiantes
    fun getAllEstudiantes(): LiveData<List<Estudiante>> {
        return estudianteDao.getAllEstudiantes()
    }

    // Insertar un estudiante en un hilo de fondo
    suspend fun insert(estudiante: Estudiante) {
        // Realizar la inserción en un hilo de fondo (IO)
        withContext(Dispatchers.IO) {
            estudianteDao.insert(estudiante)
        }
    }
}
