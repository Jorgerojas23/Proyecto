package com.example.estudiantes.DAO

import androidx.room.*
import com.example.estudiantes.Model.Curso
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    @Query("SELECT * FROM cursos")
    fun obtenerCursos(): Flow<List<Curso>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarCurso(curso: Curso)

    @Update
    suspend fun actualizarCurso(curso: Curso)

    @Delete
    suspend fun eliminarCurso(curso: Curso)
}
