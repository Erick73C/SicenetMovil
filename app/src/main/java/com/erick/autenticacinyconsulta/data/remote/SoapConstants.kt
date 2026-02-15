package com.erick.autenticacinyconsulta.data.remote

object SoapConstants {

    const val BASE_URL =
        "https://sicenet.surguanajuato.tecnm.mx/ws/"

    const val LOGIN_ACTION =
        "http://tempuri.org/accesoLogin"

    const val PERFIL_ACTION =
        "http://tempuri.org/getAlumnoAcademico"

    const val CALIF_UNIDADES_ACTION =
        "http://tempuri.org/getCalifUnidadesByAlumno"

    const val CALIF_FINAL_ACTION =
        "http://tempuri.org/getAllCalifFinalByAlumnos"

    const val CONTENT_TYPE = "text/xml; charset=utf-8"
}