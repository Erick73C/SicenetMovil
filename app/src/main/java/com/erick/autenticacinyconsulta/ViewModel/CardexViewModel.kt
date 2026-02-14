package com.erick.autenticacinyconsulta.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.local.entity.CardexEntity
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.sync.CardexSyncManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


class CardexViewModel(
    private val localRepository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModel() {

    val cardex: StateFlow<List<CardexEntity>> =
        localRepository.obtenerCardex()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val ultimaActualizacion: StateFlow<Long?> =
        localRepository.obtenerUltimaActualizacionCardex()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun sincronizar() {
        CardexSyncManager.sincronizar(workManager)
    }
}

