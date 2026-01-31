package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erick.autenticacinyconsulta.data.model.LoginResult
import com.erick.autenticacinyconsulta.data.remote.SoapRequestBuilder
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import kotlinx.coroutines.launch
class LoginViewModel(
    private val snRepository: SNRepository
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
}