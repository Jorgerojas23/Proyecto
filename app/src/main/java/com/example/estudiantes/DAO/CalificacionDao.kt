package com.example.estudiantes.DAO

import androidx.room.*
import com.example.estudiantes.Model.Calificacion
import kotlinx.coroutines.flow.Flow

@Dao
interface CalificacionDao {
    @Query("SELECT * FROM calificaciones")
    fun getAllCalificaciones(): Flow<List<Calificacion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalificacion(calificacion: Calificacion)

    @Update
    suspend fun updateCalificacion(calificacion: Calificacion)

    @Delete
    suspend fun deleteCalificacion(calificacion: Calificacion)
}
