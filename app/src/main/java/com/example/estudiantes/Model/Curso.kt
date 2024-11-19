package com.example.estudiantes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cursos")
data class Curso(
    @PrimaryKey(autoGenerate = true)
    val curso_id: Int = 0,
    val nombreCurso: String,
    val profesorId: Int
)
