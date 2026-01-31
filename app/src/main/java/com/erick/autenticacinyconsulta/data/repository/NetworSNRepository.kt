package com.erick.autenticacinyconsulta.data.repository

import android.util.Log
import com.erick.autenticacinyconsulta.data.model.LoginResult
import com.erick.autenticacinyconsulta.data.remote.SICENETWService
import com.erick.autenticacinyconsulta.data.remote.SoapRequestBuilder
import com.erick.autenticacinyconsulta.data.repository.SNRepository

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class NetworSNRepository(
    private val snApiService: SICENETWService
) : SNRepository {

    override suspend fun acceso(
        m: String,
        p: String
    ): LoginResult {

        val body = SoapRequestBuilder.login(m, p)
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.acceso(body)

        val xml = response.string()
        Log.d("SICENET_XML", xml)

        val accesoCorrecto =
            xml.contains("\"acceso\":true")

        Log.d("SICENET_LOGIN", "Acceso correcto = $accesoCorrecto")

        return if (accesoCorrecto) {
            LoginResult(
                success = true,
                message = "Login correcto"
            )
        } else {
            LoginResult(
                success = false,
                message = "Credenciales inválidas"
            )
        }
    }
}
