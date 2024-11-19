package com.example.estudiantes.Repository

import com.example.estudiantes.DAO.CalificacionDao
import com.example.estudiantes.Model.Calificacion
import kotlinx.coroutines.flow.Flow

class CalificacionRepository(private val calificacionDao: CalificacionDao) {
    val allCalificaciones: Flow<List<Calificacion>> = calificacionDao.getAllCalificaciones()

    suspend fun insert(calificacion: Calificacion) {
        calificacionDao.insertCalificacion(calificacion)
    }

    suspend fun update(calificacion: Calificacion) {
        calificacionDao.updateCalificacion(calificacion)
    }

    suspend fun delete(calificacion: Calificacion) {
        calificacionDao.deleteCalificacion(calificacion)
    }
}
