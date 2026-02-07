package com.erick.autenticacinyconsulta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil")
data class PerfilEntity(
    @PrimaryKey val matricula: String,
    val nombre: String,
    val carrera: String,
    val especialidad: String,
    val semActual: Int,
    val estatus: String,
    val modEducativo: Int,
    val creditosAcumulados: Int,
    val creditosActuales: Int,
    val fechaReins: String,
    val ultimaActualizacion: Long
)