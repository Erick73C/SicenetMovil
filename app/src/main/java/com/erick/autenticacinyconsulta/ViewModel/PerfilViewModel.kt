package com.erick.autenticacinyconsulta.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erick.autenticacinyconsulta.data.local.entity.PerfilEntity
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val localRepository: LocalSNRepository
) : ViewModel() {

    private val _perfil = MutableStateFlow<PerfilEntity?>(null)
    val perfil: StateFlow<PerfilEntity?> = _perfil

    fun cargarPerfil() {
        viewModelScope.launch {
            _perfil.value = localRepository.obtenerPerfil()
        }
    }
}
