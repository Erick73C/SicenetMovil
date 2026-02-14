package com.erick.autenticacinyconsulta.data.sync
//cris
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.worker.SicenetCalificacionesDbWorker
import com.erick.autenticacinyconsulta.data.worker.SicenetCalificacionesWorker

object CalificacionesSyncManager {

    fun sincronizar(workManager: WorkManager) {

        val red = OneTimeWorkRequestBuilder<SicenetCalificacionesWorker>()
            .addTag("WM_CALIF_RED")
            .build()

        val db = OneTimeWorkRequestBuilder<SicenetCalificacionesDbWorker>()
            .addTag("WM_CALIF_DB")
            .build()

        workManager
            .beginUniqueWork(
                "WM_CALIF",
                ExistingWorkPolicy.REPLACE,
                red
            )
            .then(db)
            .enqueue()
    }
}
