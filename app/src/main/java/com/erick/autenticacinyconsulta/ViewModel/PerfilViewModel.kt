package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val snRepository: SNRepository
) : ViewModel() {

    fun cargarPerfil() {
        viewModelScope.launch {
            try {
                val xml = snRepository.obtenerPerfil()
                Log.d("SICENET_PERFIL_VM", xml)
            } catch (e: Exception) {
                Log.e("SICENET_PERFIL_VM", "Error al cargar perfil", e)
            }
        }
    }
}
