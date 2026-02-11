package com.erick.autenticacinyconsulta.data.sync

import androidx.work.*
import com.erick.autenticacinyconsulta.data.worker.SicenetCargaAcademicaDbWorker
import com.erick.autenticacinyconsulta.data.worker.SicenetCargaAcademicaWorker

object CargaAcademicaSyncManager {

    fun sincronizar(workManager: WorkManager) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val red = OneTimeWorkRequestBuilder<SicenetCargaAcademicaWorker>()
            .setConstraints(constraints)
            .addTag("WM_CARGA_RED")
            .build()

        val db = OneTimeWorkRequestBuilder<SicenetCargaAcademicaDbWorker>()
            .addTag("WM_CARGA_DB")
            .build()

        workManager
            .beginUniqueWork(
                "WM_CARGA_ACADEMICA",
                ExistingWorkPolicy.REPLACE,
                red
            )
            .then(db)
            .enqueue()
    }
}
