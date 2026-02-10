package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import com.erick.autenticacinyconsulta.data.worker.SicenetPerfilDbWorker
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
                    encolarWorkersPerfil()
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
    private fun encolarWorkersPerfil() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerPerfilRed = OneTimeWorkRequestBuilder<SicenetPerfilWorker>()
            .setConstraints(constraints)
            .addTag("WM_PERFIL_RED")
            .build()

        val workerPerfilDb = OneTimeWorkRequestBuilder<SicenetPerfilDbWorker>()
            .addTag("WM_PERFIL_DB")
            .build()

        workManager
            .beginWith(workerPerfilRed)
            .then(workerPerfilDb)
            .enqueue()

        Log.d("WM_CHAIN", "Worker 1 → Worker 2 encadenados")
    }

}