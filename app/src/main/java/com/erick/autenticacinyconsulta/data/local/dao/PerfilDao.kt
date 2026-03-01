package com.erick.autenticacinyconsulta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erick.autenticacinyconsulta.data.local.entity.PerfilEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPerfil(perfil: PerfilEntity)

    @Query("SELECT * FROM perfil WHERE matricula = :mat")
    fun obtenerPerfil(mat: String): Flow<PerfilEntity?>

    @Query("DELETE FROM perfil")
    suspend fun limpiar()
}