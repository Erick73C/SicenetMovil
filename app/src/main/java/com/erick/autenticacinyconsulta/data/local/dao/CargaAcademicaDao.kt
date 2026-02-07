package com.erick.autenticacinyconsulta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erick.autenticacinyconsulta.data.local.entity.CargaAcademicaEntity

@Dao
interface CargaAcademicaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodo(carga: List<CargaAcademicaEntity>)

    @Query("SELECT * FROM carga_academica")
    suspend fun obtenerCargaAcademica(): List<CargaAcademicaEntity>

    @Query("DELETE FROM carga_academica")
    suspend fun limpiar()
}