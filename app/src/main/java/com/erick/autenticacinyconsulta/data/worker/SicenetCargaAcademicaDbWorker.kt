package com.erick.autenticacinyconsulta.data.worker
// Cris

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.mapper.CargaAcademicaXmlParser
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository

class SicenetCargaAcademicaDbWorker(
    context: Context,
    params: WorkerParameters,
    private val localRepository: LocalSNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val xml = inputData.getString("carga_xml") ?: return Result.failure()

            Log.d("WM_CARGA_DB", "Procesando XML de carga académica")

            val perfil = localRepository.obtenerPerfil()
                ?: return Result.failure()

            val lista = CargaAcademicaXmlParser.parse(
                xml = xml,
                matricula = perfil.matricula,
                semestre = perfil.semActual
            )

            // limpiar datos anteriores para evitar materias duplicadas
            localRepository.guardarCargaAcademica(lista)

            Log.d("WM_CARGA_DB", "Carga académica actualizada correctamente (${lista.size} registros)")

            Result.success()
        } catch (e: Exception) {
            Log.e("WM_CARGA_DB", "Error guardando carga académica", e)
            Result.failure()
        }
    }
}