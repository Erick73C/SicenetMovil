package com.erick.autenticacinyconsulta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carga_academica")
data class CargaAcademicaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val grupo: String,
    val materia: String,
    val docente: String,
    val creditos: Int,
    val ultimaActualizacion: Long
)