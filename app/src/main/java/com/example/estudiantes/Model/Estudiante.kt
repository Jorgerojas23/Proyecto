package com.example.estudiantes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "estudiantes")
data class Estudiante(
    @PrimaryKey(autoGenerate = true) val estudiante_id: Int = 0,
    val nombre: String,
    val apellido: String,
    val identificacion: Int,
    val fecha_nacimiento: Date
)
