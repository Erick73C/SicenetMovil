package com.erick.autenticacinyconsulta.data.repository

import android.content.Context
import com.erick.autenticacinyconsulta.data.remote.AddCookiesInterceptor
import com.erick.autenticacinyconsulta.data.remote.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient

object NetworkClientProvider {

    fun provideClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()

            // FORZAR HTTP 1.1
            .protocols(listOf(okhttp3.Protocol.HTTP_1_1))

            // AUMENTAR TIMEOUTS para ver las respuestas porque si no aveces tarda un monton en hacer peticiones
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)

            .addInterceptor(AddCookiesInterceptor(context))
            .addInterceptor(ReceivedCookiesInterceptor(context))

            .build()
    }
}
