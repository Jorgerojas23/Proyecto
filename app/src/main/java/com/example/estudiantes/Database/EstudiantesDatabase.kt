package com.example.estudiantes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.example.estudiantes.DAO.*
import com.example.estudiantes.Model.*

@Database(
    entities = [Estudiante::class, Curso::class, Profesor::class, Inscripcion::class, Calificacion::class],
    version = 2, // Asegúrate de que la versión sea 2 (o superior)
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EstudiantesDatabase : RoomDatabase() {

    abstract fun estudianteDao(): EstudianteDao
    abstract fun cursoDao(): CursoDao
    abstract fun profesorDao(): ProfesorDao
    abstract fun inscripcionDao(): InscripcionDao
    abstract fun calificacionDao(): CalificacionDao

    companion object {
        @Volatile
        private var INSTANCE: EstudiantesDatabase? = null

        // Definir la migración de la versión 1 a la 2 (si fuera necesario)
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Ejemplo: Si se agrega una nueva columna a la tabla Estudiante
                database.execSQL("ALTER TABLE estudiante ADD COLUMN nuevo_campo TEXT")
            }
        }

        fun getDatabase(context: Context): EstudiantesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EstudiantesDatabase::class.java,
                    "estudiantes_database"
                )
                    .addMigrations(MIGRATION_1_2) // Agregar migración
                    .fallbackToDestructiveMigration() // Esto es para que se destruya la base de datos si no tienes una migración definida
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
