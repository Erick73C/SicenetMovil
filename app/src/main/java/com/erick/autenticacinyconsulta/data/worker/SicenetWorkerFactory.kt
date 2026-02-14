package com.erick.autenticacinyconsulta.data.worker
// Cris

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.repository.SNRepository


class SicenetWorkerFactory(
    private val networkRepository: SNRepository,
    private val localRepository: LocalSNRepository
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

            SicenetPerfilDbWorker::class.java.name ->
                SicenetPerfilDbWorker(
                    appContext,
                    workerParameters,
                    localRepository
                )

            SicenetCalificacionesWorker::class.java.name ->
                SicenetCalificacionesWorker(
                    appContext,
                    workerParameters,
                    networkRepository
                )

            SicenetCalificacionesDbWorker::class.java.name ->
                SicenetCalificacionesDbWorker(
                    appContext,
                    workerParameters,
                    localRepository
                )

            SicenetCargaAcademicaWorker::class.java.name ->
                SicenetCargaAcademicaWorker(
                    appContext,
                    workerParameters,
                    networkRepository
                )

            SicenetCargaAcademicaDbWorker::class.java.name ->
                SicenetCargaAcademicaDbWorker(
                    appContext,
                    workerParameters,
                    localRepository
                )

            else -> null
        }
    }
}
