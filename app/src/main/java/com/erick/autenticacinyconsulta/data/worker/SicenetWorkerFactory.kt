package com.erick.autenticacinyconsulta.data.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import androidx.work.ListenableWorker

class SicenetWorkerFactory(
    private val networkRepository: SNRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            SicenetPerfilWorker::class.java.name ->
                SicenetPerfilWorker(
                    appContext,
                    workerParameters,
                    networkRepository
                )

            else -> null
        }
    }
}