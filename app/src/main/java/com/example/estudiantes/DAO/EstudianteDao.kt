    package com.example.estudiantes.DAO

    import androidx.lifecycle.LiveData
    import androidx.room.*
    import com.example.estudiantes.Model.Estudiante

    @Dao
    interface EstudianteDao {

        @Query("SELECT * FROM estudiantes WHERE identificacion = :identificacion LIMIT 1")
        suspend fun getEstudiantePorIdentificacion(identificacion: Int): Estudiante?

        @Insert
        suspend fun insert(estudiante: Estudiante)

        @Query("SELECT * FROM estudiantes")
        fun getAllEstudiantes(): LiveData<List<Estudiante>>
    }

