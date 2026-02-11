package com.erick.autenticacinyconsulta.data.repository

import android.content.Context
import com.erick.autenticacinyconsulta.data.remote.AddCookiesInterceptor
import com.erick.autenticacinyconsulta.data.remote.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient

object NetworkClientProvider {

    fun provideClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor(context))
            .addInterceptor(ReceivedCookiesInterceptor(context))
            .build()
    }
}
