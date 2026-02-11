package com.erick.autenticacinyconsulta.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.erick.autenticacinyconsulta.data.remote.SoapRequestBuilder
import com.erick.autenticacinyconsulta.data.repository.NetworkClientProvider
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class SicenetCargaAcademicaWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("WM_CARGA_RED", "Consultando carga académica")

            val client = NetworkClientProvider.provideClient(applicationContext)

            val body = SoapRequestBuilder.cargaAcademica()
                .toRequestBody("text/xml; charset=utf-8".toMediaType())

            val request = Request.Builder()
                .url("https://sicenet.surguanajuato.tecnm.mx/ws/wsalumnos.asmx")
                .post(body)
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader(
                    "SOAPAction",
                    "http://tempuri.org/getCargaAcademicaByAlumno"
                )
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                Log.e(
                    "WM_CARGA_RED",
                    "Error HTTP ${response.code} - ${response.message}"
                )
                return Result.failure()
            }

            val xml = response.body?.string()

            if (xml.isNullOrEmpty()) {
                Log.e("WM_CARGA_RED", "XML vacío")
                return Result.failure()
            }

            Log.d("WM_CARGA_RED", "XML recibido correctamente")

            Result.success(
                workDataOf("carga_xml" to xml)
            )

        } catch (e: Exception) {
            Log.e("WM_CARGA_RED", "Error en carga académica", e)
            Result.failure()
        }
    }
}
