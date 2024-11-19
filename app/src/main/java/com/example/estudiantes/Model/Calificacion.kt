package com.example.estudiantes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calificaciones")
data class Calificacion(
    @PrimaryKey(autoGenerate = true) val calificacion_id: Int = 0,
    val inscripcion_id: Int,
    val nota: Double // Usa Double para representar DECIMAL
)
