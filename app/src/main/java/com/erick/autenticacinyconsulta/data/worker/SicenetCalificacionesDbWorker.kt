package com.erick.autenticacinyconsulta.data.worker
// Cris
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.mapper.CalificacionesXmlParser
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository

class SicenetCalificacionesDbWorker(
    context: Context,
    params: WorkerParameters,
    private val localRepository: LocalSNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val xml = inputData.getString("calif_xml")
                ?: return Result.failure()

            Log.d("WM_CALIF_DB", "Procesando XML de calificaciones")

            val resultado = CalificacionesXmlParser.parse(xml)

            // guardar calif final
            localRepository.guardarCalificacionesFinales(resultado.finales)

            // guardar calif unidad
            localRepository.guardarCalificacionesUnidad(resultado.unidades)

            Log.d("WM_CALIF_DB", "Calificaciones guardadas en Room")
            Result.success()
        } catch (e: Exception) {
            Log.e("WM_CALIF_DB", "Error guardando calificaciones", e)
            Result.failure()
        }
    }
}