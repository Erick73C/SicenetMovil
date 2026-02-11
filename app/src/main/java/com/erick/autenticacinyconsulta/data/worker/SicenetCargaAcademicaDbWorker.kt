package com.erick.autenticacinyconsulta.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.local.db.SicenetDatabase
import com.erick.autenticacinyconsulta.data.mapper.CargaAcademicaXmlParser
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository

class SicenetCargaAcademicaDbWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val xml = inputData.getString("carga_xml") ?: return Result.failure()

        val db = SicenetDatabase.Companion.getDatabase(applicationContext)

        val localRepo = LocalSNRepository(
            db.perfilDao(),
            db.cargaAcademicaDao(),
            db.cardexDao(),
            db.calificacionUnidadDao(),
            db.calificacionFinalDao()
        )

        val perfil = db.perfilDao().obtenerPerfil()
            ?: return Result.failure()

        val lista = CargaAcademicaXmlParser.parse(
            xml = xml,
            matricula = perfil.matricula,
            semestre = perfil.semActual
        )

        localRepo.guardarCargaAcademica(lista)

        Log.d("WM_CARGA_DB", "Carga académica guardada (${lista.size})")

        return Result.success()
    }
}