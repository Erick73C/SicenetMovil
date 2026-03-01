package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.SessionManager
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import com.erick.autenticacinyconsulta.data.worker.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

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
                    val usuarioNormalizado = usuario.uppercase()
                    SessionManager.iniciarSesion(usuarioNormalizado)
                    encolarWorkersSincronizacion()
                    onSuccess()
                } else {
                    onError("Credenciales inválidas")
                }

            } catch (e: Exception) {

                val usuarioNormalizado = usuario.trim().uppercase()

                Log.d("LOGIN_OFFLINE", "Buscando usuario: '$usuarioNormalizado'")

                val perfilLocal = localRepository
                    .obtenerPerfil(usuarioNormalizado)
                    .first()

                if (perfilLocal != null) {


                    SessionManager.iniciarSesion(usuarioNormalizado)

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

        val workerPerfilRed = OneTimeWorkRequestBuilder<SicenetPerfilWorker>()
            .setConstraints(constraints)
            .build()

        val workerPerfilDb = OneTimeWorkRequestBuilder<SicenetPerfilDbWorker>()
            .build()

        val workerCargaRed = OneTimeWorkRequestBuilder<SicenetCargaAcademicaWorker>()
            .setConstraints(constraints)
            .build()

        val workerCargaDb = OneTimeWorkRequestBuilder<SicenetCargaAcademicaDbWorker>()
            .build()

        val workerCalifRed = OneTimeWorkRequestBuilder<SicenetCalificacionesWorker>()
            .setConstraints(constraints)
            .build()

        val workerCalifDb = OneTimeWorkRequestBuilder<SicenetCalificacionesDbWorker>()
            .build()

        // PERFIL
        workManager.beginWith(workerPerfilRed)
            .then(workerPerfilDb)
            .enqueue()

        // CARGA
        workManager.beginWith(workerCargaRed)
            .then(workerCargaDb)
            .enqueue()

        // CALIFICACIONES
        workManager.beginWith(workerCalifRed)
            .then(workerCalifDb)
            .enqueue()
    }
}