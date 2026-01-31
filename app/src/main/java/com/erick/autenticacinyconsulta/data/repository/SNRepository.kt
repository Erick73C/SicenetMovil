package com.erick.autenticacinyconsulta.data.repository

import com.erick.autenticacinyconsulta.data.model.AlumnoPerfil
import com.erick.autenticacinyconsulta.data.model.LoginResult

interface SNRepository {
    suspend fun acceso(usuario: String, password: String): LoginResult
    suspend fun obtenerPerfil(): AlumnoPerfil
}

