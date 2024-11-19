package com.example.estudiantes.Repository

import android.app.Application
import com.example.estudiantes.Database.EstudiantesDatabase
import com.example.estudiantes.Model.Curso
import kotlinx.coroutines.flow.Flow

class CursoRepository(application: Application) {
    private val cursoDao = EstudiantesDatabase.getDatabase(application).cursoDao()

    fun obtenerCursos(): Flow<List<Curso>> = cursoDao.obtenerCursos()

    suspend fun agregarCurso(curso: Curso) = cursoDao.agregarCurso(curso)

    suspend fun actualizarCurso(curso: Curso) = cursoDao.actualizarCurso(curso)

    suspend fun eliminarCurso(curso: Curso) = cursoDao.eliminarCurso(curso)
}
