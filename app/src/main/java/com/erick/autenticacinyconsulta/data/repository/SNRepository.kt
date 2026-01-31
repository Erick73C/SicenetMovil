package com.erick.autenticacinyconsulta.data.repository

import com.erick.autenticacinyconsulta.data.model.LoginResult

interface SNRepository {
    suspend fun acceso(
        m: String,
        p: String
    ): LoginResult
}
