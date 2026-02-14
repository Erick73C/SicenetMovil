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

            val xml = repository.obtenerCalificacionesXml()

            Log.d("CALIF_XML_COMPLETO", xml)
            Log.d("WM_CALIF_RED", "XML recibido correctamente")

            Result.success(
                workDataOf("calif_xml" to xml)
            )

        } catch (e: Exception) {
            Log.e("WM_CALIF_RED", "Error", e)
            Result.failure()
        }
    }
}
