package com.erick.autenticacinyconsulta.data.sync

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.worker.SicenetCardexDbWorker
import com.erick.autenticacinyconsulta.data.worker.SicenetCardexWorker

object CardexSyncManager {

    fun sincronizar(workManager: WorkManager) {

        val red = OneTimeWorkRequestBuilder<SicenetCardexWorker>()
            .addTag("WM_CARDEX_RED")
            .build()

        val db = OneTimeWorkRequestBuilder<SicenetCardexDbWorker>()
            .addTag("WM_CARDEX_DB")
            .build()

        workManager
            .beginUniqueWork(
                "WM_CARDEX",
                ExistingWorkPolicy.REPLACE,
                red
            )
            .then(db)
            .enqueue()
    }
}
