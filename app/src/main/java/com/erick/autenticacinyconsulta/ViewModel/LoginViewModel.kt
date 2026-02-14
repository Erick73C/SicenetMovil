package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import com.erick.autenticacinyconsulta.data.worker.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val snRepository: SNRepository,        // RED
    private val localRepository: LocalSNRepository,// Local (Rom)
    private val workManager: WorkManager
) : ViewModel() {

    fun login(
        usuario: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // intentar login online
                val result = snRepository.acceso(usuario, password)

                if (result.success) {
                    encolarWorkersSincronizacion()
                    onSuccess()
                } else {
                    onError("Credenciales inválidas")
                }

            } catch (e: Exception) {
                val perfilLocal = localRepository.obtenerPerfil()

                if (perfilLocal != null) {
                    Log.d("LOGIN_OFFLINE", "Perfil encontrado en Room")
                    onSuccess()
                } else {
                    onError("Sin conexión y sin datos guardados")
                }
            }
        }
    }

    // inicia la sincronización de toda la informacion del alumno en segundo plano
    private fun encolarWorkersSincronizacion() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 1 configurar descarga de perfil
        val workerPerfilRed = OneTimeWorkRequestBuilder<SicenetPerfilWorker>()
            .setConstraints(constraints)
            .addTag("WM_PERFIL_RED")
            .build()
        val workerPerfilDb = OneTimeWorkRequestBuilder<SicenetPerfilDbWorker>()
            .addTag("WM_PERFIL_DB")
            .build()

        // 2 configurar descarga de acrga academica (lista de todas las materias)
        val workerCargaRed = OneTimeWorkRequestBuilder<SicenetCargaAcademicaWorker>()
            .setConstraints(constraints)
            .addTag("WM_CARGA_RED")
            .build()
        val workerCargaDb = OneTimeWorkRequestBuilder<SicenetCargaAcademicaDbWorker>()
            .addTag("WM_CARGA_DB")
            .build()

        // 3 configurar descarga de calificaciones actuales
        val workerCalifRed = OneTimeWorkRequestBuilder<SicenetCalificacionesWorker>()
            .setConstraints(constraints)
            .addTag("WM_CALIF_RED")
            .build()
        val workerCalifDb = OneTimeWorkRequestBuilder<SicenetCalificacionesDbWorker>()
            .addTag("WM_CALIF_DB")
            .build()

        // ejecutar workers
        workManager
            .beginWith(listOf(workerPerfilRed, workerCargaRed, workerCalifRed))
            .then(listOf(workerPerfilDb, workerCargaDb, workerCalifDb))
            .enqueue()
    }
}