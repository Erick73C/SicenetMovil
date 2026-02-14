package com.erick.autenticacinyconsulta.data.repository

import com.erick.autenticacinyconsulta.data.model.AlumnoPerfil
import com.erick.autenticacinyconsulta.data.model.LoginResult

interface SNRepository {

    suspend fun acceso(matricula: String, password: String): LoginResult
    suspend fun obtenerPerfil(): AlumnoPerfil
    suspend fun obtenerPerfilJson(): String

    suspend fun obtenerCardexXml(): String
}