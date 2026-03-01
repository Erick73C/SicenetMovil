package com.erick.autenticacinyconsulta.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erick.autenticacinyconsulta.data.local.entity.PerfilEntity
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val localRepository: LocalSNRepository
) : ViewModel() {

    fun obtenerPerfil(matricula: String): Flow<PerfilEntity?> {
        return localRepository.obtenerPerfil(matricula)
    }
}
