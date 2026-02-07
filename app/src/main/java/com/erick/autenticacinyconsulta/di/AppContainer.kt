package com.erick.autenticacinyconsulta.di

import android.content.Context
import com.erick.autenticacinyconsulta.data.local.db.SicenetDatabase
import com.erick.autenticacinyconsulta.data.remote.AddCookiesInterceptor
import com.erick.autenticacinyconsulta.data.remote.ReceivedCookiesInterceptor
import com.erick.autenticacinyconsulta.data.remote.SICENETWService
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.repository.NetworSNRepository
import com.erick.autenticacinyconsulta.data.repository.SNRepository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer {
    val networkSNRepository: SNRepository
    val localSNRepository: LocalSNRepository
}

class DefaultAppContainer(
    context: Context
) : AppContainer {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor(context))
        .addInterceptor(ReceivedCookiesInterceptor(context))
        .build()

    private val retrofitSN: Retrofit = Retrofit.Builder()
        .baseUrl("https://sicenet.surguanajuato.tecnm.mx/ws/")
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        .client(okHttpClient)
        .build()

    private val sicenetService: SICENETWService by lazy {
        retrofitSN.create(SICENETWService::class.java)
    }

    override val networkSNRepository: SNRepository by lazy {
        NetworSNRepository(sicenetService)
    }


    private val database: SicenetDatabase by lazy {
        SicenetDatabase.getDatabase(context)
    }


    override val localSNRepository: LocalSNRepository by lazy {
        LocalSNRepository(
            perfilDao = database.perfilDao(),
            cargaAcademicaDao = database.cargaAcademicaDao(),
            cardexDao = database.cardexDao(),
            calificacionUnidadDao = database.calificacionUnidadDao(),
            calificacionFinalDao = database.calificacionFinalDao()
        )
    }
}