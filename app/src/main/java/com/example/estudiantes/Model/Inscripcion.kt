package com.example.estudiantes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "inscripciones")
data class Inscripcion(
    @PrimaryKey(autoGenerate = true) val inscripcion_id: Int = 0,
    val estudiante_id: Int,
    val curso_id: Int,
    val fecha_inscripcion: Date
)
