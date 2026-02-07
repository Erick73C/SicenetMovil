package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.model.LoginResult
import com.erick.autenticacinyconsulta.data.remote.SoapRequestBuilder
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import com.erick.autenticacinyconsulta.data.worker.SicenetPerfilWorker
import kotlinx.coroutines.launch
class LoginViewModel(
    private val snRepository: SNRepository,
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

                Log.d("SICENET_VM", "Resultado login: $result")

                if (result.success) {
                    encolarWorkerPerfil()
                    onSuccess()
                } else {
                    onError("Credenciales inválidas")
                }
            } catch (e: Exception) {
                Log.e("SICENET_VM", "Error login", e)
                onError("Error de conexión")
            }
        }
    }

    private fun encolarWorkerPerfil() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SicenetPerfilWorker>()
            .setConstraints(constraints)
            .addTag("WM_PERFIL")
            .build()

        workManager.enqueueUniqueWork(
            "WM_PERFIL",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )

        Log.d("WM_PERFIL", "Worker 1 encolado correctamente")
    }
}