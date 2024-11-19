package com.example.estudiantes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profesores")
data class Profesor(
    @PrimaryKey(autoGenerate = true) val profesor_id: Int = 0,
    val nombre: String,
    val apellido: String,
    val identificacion: String,
    val telefono: String
)
