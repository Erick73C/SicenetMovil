package com.erick.autenticacinyconsulta.data.worker

import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.local.entity.PerfilEntity
import com.erick.autenticacinyconsulta.data.model.AlumnoPerfil
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository

import com.google.gson.Gson
import android.content.Context

class SicenetPerfilDbWorker(
    context: Context,
    params: WorkerParameters,
    private val localRepository: LocalSNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val perfilJson = inputData.getString("perfil_json")

            if (perfilJson.isNullOrEmpty()) {
                Log.e("WM_PERFIL_DB", "No llegó perfil_json")
                return Result.failure()
            }

            Log.d("WM_PERFIL_DB", "JSON recibido")

            val perfil = Gson().fromJson(perfilJson, AlumnoPerfil::class.java)

            val entity = PerfilEntity(
                matricula = perfil.matricula,
                nombre = perfil.nombre,
                carrera = perfil.carrera,
                especialidad = perfil.especialidad,
                estatus = perfil.estatus,
                semActual = perfil.semActual,
                cdtosAcumulados = perfil.cdtosAcumulados,
                cdtosActuales = perfil.cdtosActuales,
                fechaReins = perfil.fechaReins,
                modEducativo = perfil.modEducativo,
                urlFoto = perfil.urlFoto,
                inscrito = perfil.inscrito,
                ultimaActualizacion = System.currentTimeMillis()
            )

            localRepository.guardarPerfil(entity)

            Log.d("WM_PERFIL_DB", "Perfil guardado en Room")

            Result.success()

        } catch (e: Exception) {
            Log.e("WM_PERFIL_DB", "Error guardando perfil", e)
            Result.failure()
        }
    }
}
