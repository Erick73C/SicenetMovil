package com.erick.autenticacinyconsulta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erick.autenticacinyconsulta.data.local.entity.CardexEntity

@Dao
interface CardexDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodo(cardex: List<CardexEntity>)

    @Query("SELECT * FROM cardex")
    suspend fun obtenerCardex(): List<CardexEntity>

    @Query("DELETE FROM cardex")
    suspend fun limpiar()
}