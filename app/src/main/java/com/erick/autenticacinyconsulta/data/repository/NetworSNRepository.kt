package com.erick.autenticacinyconsulta.data.repository

import android.util.Log
import com.erick.autenticacinyconsulta.data.model.AlumnoPerfil
import com.erick.autenticacinyconsulta.data.model.LoginResult
import com.erick.autenticacinyconsulta.data.remote.SICENETWService
import com.erick.autenticacinyconsulta.data.remote.SoapRequestBuilder
import com.google.gson.Gson

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

        val accesoCorrecto = xml.contains("\"acceso\":true")

        Log.d("SICENET_LOGIN", "Acceso correcto = $accesoCorrecto")

        return if (accesoCorrecto) {
            LoginResult(true, "Login correcto")
        } else {
            LoginResult(false, "Credenciales inválidas")
        }
    }

    override suspend fun obtenerPerfilJson(): String {

        val body = SoapRequestBuilder.perfil()
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.getAlumnoAcademico(body)
        val xml = response.string()

        val json = extraerJson(xml)

        Log.d("SICENET_PERFIL_JSON", json)

        return json
    }

    override suspend fun obtenerPerfil(): AlumnoPerfil {

        val json = obtenerPerfilJson()

        return Gson().fromJson(json, AlumnoPerfil::class.java)
    }

    private fun extraerJson(xml: String): String {
        return xml.substringAfter("<getAlumnoAcademicoResult>")
            .substringBefore("</getAlumnoAcademicoResult>")
    }

    override suspend fun obtenerCardexXml(): String {

        val body = SoapRequestBuilder.cardex(1)
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.getCardex(body)
        return response.string()
    }
// cris
    override suspend fun obtenerCalificacionesUnidadesXml(): String {
        val body = SoapRequestBuilder.calificacionesUnidades()
            .toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.getCalifUnidadesByAlumno(body)
        return response.string()
    }

    override suspend fun obtenerCalificacionesFinalesXml(modEducativo: Int): String {
        val body = SoapRequestBuilder.calificacionesFinales(modEducativo)
            .toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.getAllCalifFinalByAlumnos(body)
        return response.string()
    }



    //para soportar la descrga de la carga academica
    override suspend fun obtenerCargaAcademicaXml(): String {
        val body = SoapRequestBuilder.cargaAcademica()
            .toRequestBody("text/xml; charset=utf-8".toMediaType())
        val response = snApiService.getCargaAcademicaByAlumno(body)
        return response.string()
    }
}
