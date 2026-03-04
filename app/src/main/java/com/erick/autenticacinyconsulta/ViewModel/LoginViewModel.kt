package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
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
                val result = snRepository.acceso(usuario, password)

                if (result.success) {

                    val usuarioNormalizado = usuario.trim().uppercase()
                    SessionManager.iniciarSesion(usuarioNormalizado)

                    encolarWorkerPerfil()

                    onSuccess()
                } else {
                    onError("Credenciales inválidas")
                }

            } catch (e: Exception) {

                val usuarioNormalizado = usuario.trim().uppercase()

                val perfilLocal = localRepository
                    .obtenerPerfil(usuarioNormalizado)
                    .first()

                if (perfilLocal != null) {

                    SessionManager.iniciarSesion(usuarioNormalizado)

                    onSuccess()
                } else {
                    onError("Sin conexión y sin datos guardados")
                }
            }
        }
    }

    private fun encolarWorkerPerfil() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerPerfilRed = OneTimeWorkRequestBuilder<SicenetPerfilWorker>()
            .setConstraints(constraints)
            .build()

        val workerPerfilDb = OneTimeWorkRequestBuilder<SicenetPerfilDbWorker>()
            .build()

        workManager
            .beginUniqueWork(
                "perfil_sync",
                ExistingWorkPolicy.KEEP,
                workerPerfilRed
            )
//            esto evita que:
//                 Que se creen múltiples workers si el usuario intenta loguearse varias veces
//                Que se duplique sincronización
            .then(workerPerfilDb)
            .enqueue()
    }
}