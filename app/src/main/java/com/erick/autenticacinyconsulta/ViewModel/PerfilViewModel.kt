package com.erick.autenticacinyconsulta.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erick.autenticacinyconsulta.data.model.AlumnoPerfil
import com.erick.autenticacinyconsulta.data.repository.SNRepository
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val snRepository: SNRepository
) : ViewModel() {

    var perfil by mutableStateOf<AlumnoPerfil?>(null)
        private set

    fun cargarPerfil() {
        viewModelScope.launch {
            perfil = snRepository.obtenerPerfil()
        }
    }
}
