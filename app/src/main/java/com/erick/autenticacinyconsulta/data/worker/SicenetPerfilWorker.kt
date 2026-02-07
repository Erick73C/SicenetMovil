package com.erick.autenticacinyconsulta.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.repository.SNRepository

class SicenetPerfilWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: SNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        Log.d("WM_PERFIL", "🚀 Worker iniciado")

        return try {
            val perfil = repository.obtenerPerfil()

            Log.d("WM_PERFIL", "✅ Perfil obtenido: $perfil")

            Result.success()

        } catch (e: Exception) {
            Log.e("WM_PERFIL", "❌ Error en worker", e)
            Result.retry()
        }
    }
}
