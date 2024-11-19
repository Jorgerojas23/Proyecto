package com.example.estudiantes.Repository

import com.example.estudiantes.DAO.InscripcionDao
import com.example.estudiantes.Model.Inscripcion
import kotlinx.coroutines.flow.Flow

class InscripcionRepository(private val inscripcionDao: InscripcionDao) {
    val allInscripciones: Flow<List<Inscripcion>> = inscripcionDao.getAllInscripciones()

    suspend fun insert(inscripcion: Inscripcion) {
        inscripcionDao.insertInscripcion(inscripcion)
    }

    suspend fun update(inscripcion: Inscripcion) {
        inscripcionDao.updateInscripcion(inscripcion)
    }

    suspend fun delete(inscripcion: Inscripcion) {
        inscripcionDao.deleteInscripcion(inscripcion)
    }
}
