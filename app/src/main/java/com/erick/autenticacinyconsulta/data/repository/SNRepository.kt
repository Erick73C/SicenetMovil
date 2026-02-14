package com.erick.autenticacinyconsulta.data.repository
// Cris

import com.erick.autenticacinyconsulta.data.model.AlumnoPerfil
import com.erick.autenticacinyconsulta.data.model.LoginResult

interface SNRepository {

    suspend fun acceso(matricula: String, password: String): LoginResult
    suspend fun obtenerPerfil(): AlumnoPerfil
    suspend fun obtenerPerfilJson(): String

    suspend fun obtenerCardexXml(): String
    suspend fun obtenerCalificacionesXml(): String
    //obtiene la carga academica
    suspend fun obtenerCargaAcademicaXml(): String
}