package com.erick.autenticacinyconsulta.data.model

data class AlumnoPerfil(
    val nombre: String,
    val matricula: String,
    val carrera: String,
    val especialidad: String,
    val semActual: Int,
    val cdtosAcumulados: Int,
    val cdtosActuales: Int,
    val estatus: String,
    val inscrito: Boolean,
    val adeudo: Boolean,
    val urlFoto: String
)
