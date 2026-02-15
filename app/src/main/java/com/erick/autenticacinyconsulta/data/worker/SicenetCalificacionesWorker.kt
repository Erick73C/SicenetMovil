package com.erick.autenticacinyconsulta.data.worker
//cris
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.erick.autenticacinyconsulta.data.repository.SNRepository

class SicenetCalificacionesWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: SNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            Log.d("WM_CALIF_RED", "Consultando calificaciones")

            val unidadesXml = repository.obtenerCalificacionesUnidadesXml()
            val finalesXml = repository.obtenerCalificacionesFinalesXml(1) // Por defecto 1 o 0


            Log.d("WM_CALIF_RED", "XMLs recibidos correctamente")

            Result.success(
                workDataOf(
                    "unidades_xml" to unidadesXml,
                    "finales_xml" to finalesXml
                )
            )


        } catch (e: Exception) {
            Log.e("WM_CALIF_RED", "Error", e)
            Result.failure()
        }
    }
}
