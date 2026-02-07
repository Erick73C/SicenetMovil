package com.erick.autenticacinyconsulta.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erick.autenticacinyconsulta.data.local.dao.*
import com.erick.autenticacinyconsulta.data.local.entity.*

@Database(
    entities = [
        PerfilEntity::class,
        CargaAcademicaEntity::class,
        CardexEntity::class,
        CalificacionUnidadEntity::class,
        CalificacionFinalEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SicenetDatabase : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao
    abstract fun cargaAcademicaDao(): CargaAcademicaDao
    abstract fun cardexDao(): CardexDao
    abstract fun calificacionUnidadDao(): CalificacionUnidadDao
    abstract fun calificacionFinalDao(): CalificacionFinalDao

    companion object {
        @Volatile
        private var INSTANCE: SicenetDatabase? = null

        fun getDatabase(context: Context): SicenetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SicenetDatabase::class.java,
                    "sicenet_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}