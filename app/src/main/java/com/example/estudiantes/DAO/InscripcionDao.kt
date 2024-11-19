package com.example.estudiantes.DAO

import androidx.room.*
import com.example.estudiantes.Model.Inscripcion
import kotlinx.coroutines.flow.Flow

@Dao
interface InscripcionDao {
    @Query("SELECT * FROM inscripciones")
    fun getAllInscripciones(): Flow<List<Inscripcion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInscripcion(inscripcion: Inscripcion)

    @Update
    suspend fun updateInscripcion(inscripcion: Inscripcion)

    @Delete
    suspend fun deleteInscripcion(inscripcion: Inscripcion)
}
