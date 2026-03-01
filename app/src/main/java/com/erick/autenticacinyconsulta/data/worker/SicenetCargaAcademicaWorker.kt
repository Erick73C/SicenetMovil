package com.erick.autenticacinyconsulta.data.worker
// Cris

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.erick.autenticacinyconsulta.SessionManager
import com.erick.autenticacinyconsulta.data.repository.SNRepository

class SicenetCargaAcademicaWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: SNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            Log.d("WM_CARGA_RED", "Consultando carga académica")

            val xml = repository.obtenerCargaAcademicaXml()

            if (xml.isEmpty()) {
                Log.e("WM_CARGA_RED", "XML vacío")
                return Result.failure()
            }

            val matricula = SessionManager.matricula

            Log.d("WM_CARGA_RED", "XML recibido correctamente")

            Result.success(
                workDataOf(
                    "carga_xml" to xml,
                    "matricula" to matricula
                )
            )

        } catch (e: Exception) {
            Log.e("WM_CARGA_RED", "Error en carga académica", e)
            Result.failure()
        }
    }
}
