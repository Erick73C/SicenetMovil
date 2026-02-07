package com.erick.autenticacinyconsulta

import android.app.Application
import androidx.work.Configuration
import com.erick.autenticacinyconsulta.data.worker.SicenetWorkerFactory
import com.erick.autenticacinyconsulta.di.DefaultAppContainer

class SicenetApp : Application(), Configuration.Provider {

    lateinit var container: DefaultAppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                SicenetWorkerFactory(container.networkSNRepository)
            )
            .build()
}
