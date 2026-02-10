package com.erick.autenticacinyconsulta.data.repository

import com.erick.autenticacinyconsulta.data.local.dao.*
import com.erick.autenticacinyconsulta.data.local.entity.*

class LocalSNRepository(
    private val perfilDao: PerfilDao,
    private val cargaAcademicaDao: CargaAcademicaDao,
    private val cardexDao: CardexDao,
    private val calificacionUnidadDao: CalificacionUnidadDao,
    private val calificacionFinalDao: CalificacionFinalDao
) {


    suspend fun guardarPerfil(perfil: PerfilEntity) {
        perfilDao.insertarPerfil(perfil)
    }

    suspend fun obtenerPerfil(): PerfilEntity? {
        return perfilDao.obtenerPerfil()
    }

    suspend fun guardarCargaAcademica(carga: List<CargaAcademicaEntity>) {
        cargaAcademicaDao.limpiar()
        cargaAcademicaDao.insertarTodo(carga)
    }

    suspend fun obtenerCargaAcademica(): List<CargaAcademicaEntity> {
        return cargaAcademicaDao.obtenerCargaAcademica()
    }


    suspend fun guardarCardex(cardex: List<CardexEntity>) {
        cardexDao.limpiar()
        cardexDao.insertarTodo(cardex)
    }

    suspend fun obtenerCardex(): List<CardexEntity> {
        return cardexDao.obtenerCardex()
    }

    suspend fun guardarCalificacionesUnidad(
        calificaciones: List<CalificacionUnidadEntity>
    ) {
        calificacionUnidadDao.limpiar()
        calificacionUnidadDao.insertarTodo(calificaciones)
    }

    suspend fun obtenerCalificacionesUnidad(): List<CalificacionUnidadEntity> {
        return calificacionUnidadDao.obtenerCalificaciones()
    }

    suspend fun guardarCalificacionesFinales(
        calificaciones: List<CalificacionFinalEntity>
    ) {
        calificacionFinalDao.limpiar()
        calificacionFinalDao.insertarTodo(calificaciones)
    }

    suspend fun obtenerCalificacionesFinales(): List<CalificacionFinalEntity> {
        return calificacionFinalDao.obtenerCalificacionesFinales()
    }
}