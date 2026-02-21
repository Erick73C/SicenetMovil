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

            Log.d("WM_CALIF_RED", "Consultando calificaciones...")

            val unidadesXml = try {
                repository.obtenerCalificacionesUnidadesXml()
            } catch (e: Exception) {
                Log.e("WM_CALIF_RED", "Error obteniendo unidades", e)
                ""
            }
            
            // cris actualizacion 20/02/2026 
            // intentar primero con modEducativo 1
            var finalesXml = try {
                repository.obtenerCalificacionesFinalesXml(1)
            } catch (e: Exception) {
                Log.e("WM_CALIF_RED", "Error obteniendo finales (mod 1)", e)
                ""
            }
            
            // si no contiene datos de calificaciones (lstCalif), intentamos con 0
            if (!finalesXml.contains("lstCalif") && !finalesXml.contains("[{")) {
                Log.d("WM_CALIF_RED", "No se encontraron finales con modEducativo 1 o formato distinto, intentando con 0")
                try {
                    val finalesFallback = repository.obtenerCalificacionesFinalesXml(0)
                    if (finalesFallback.contains("lstCalif") || finalesFallback.contains("[{")) {
                        finalesXml = finalesFallback
                        Log.d("WM_CALIF_RED", "Se encontraron finales con modEducativo 0")
                    }
                } catch (e: Exception) {
                    Log.e("WM_CALIF_RED", "Error obteniendo finales (mod 0)", e)
                }
            }

            Log.d("WM_CALIF_RED", "XMLs recibidos. Unidades len: ${unidadesXml.length}, Finales len: ${finalesXml.length}")

            if (unidadesXml.isBlank() && finalesXml.isBlank()) {
                Log.w("WM_CALIF_RED", "Ambos XMLs están vacíos, devolviendo falla")
                return Result.failure()
            }

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
