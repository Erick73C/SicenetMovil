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
            xml.contains("<accesoLoginResult>true</accesoLoginResult>")

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
