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
import com.erick.autenticacinyconsulta.data.worker.SicenetPerfilDbWorker
import com.erick.autenticacinyconsulta.data.worker.SicenetPerfilWorker
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
                // 1️⃣ Intentar login ONLINE
                val result = snRepository.acceso(usuario, password)

                if (result.success) {
                    encolarWorkersPerfil()
                    onSuccess()
                } else {
                    onError("Credenciales inválidas")
                }

            } catch (e: Exception) {

                //  FLLo internet → intentar OFFLINE
                Log.w("LOGIN_OFFLINE", "Sin internet, intentando Room")

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
    }
}