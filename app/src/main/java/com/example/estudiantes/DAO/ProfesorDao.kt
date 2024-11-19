package com.example.estudiantes.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.estudiantes.Model.Profesor

@Dao
interface ProfesorDao {

    // Modificar para aceptar identificacion como String
    @Query("SELECT * FROM profesores WHERE identificacion = :identificacion LIMIT 1")
    suspend fun getProfesorPorIdentificacion(identificacion: String): Profesor?

    // De igual forma, cambia la firma del parámetro a String
    @Query("SELECT EXISTS (SELECT 1 FROM profesores WHERE identificacion = :identificacion LIMIT 1)")
    suspend fun existeIdentificacion(identificacion: String): Boolean

    @Insert()
    suspend fun insert(profesor: Profesor)

    @Query("SELECT * FROM profesores")
    fun getAllProfesores(): LiveData<List<Profesor>>
}
