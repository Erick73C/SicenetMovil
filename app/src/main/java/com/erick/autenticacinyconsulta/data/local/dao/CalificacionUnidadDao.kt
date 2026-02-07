package com.erick.autenticacinyconsulta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionUnidadEntity

@Dao
interface CalificacionUnidadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodo(calificaciones: List<CalificacionUnidadEntity>)

    @Query("SELECT * FROM calificaciones_unidad")
    suspend fun obtenerCalificaciones(): List<CalificacionUnidadEntity>

    @Query("DELETE FROM calificaciones_unidad")
    suspend fun limpiar()
}