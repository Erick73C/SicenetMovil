package com.erick.autenticacinyconsulta.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.SessionManager
import com.erick.autenticacinyconsulta.data.local.entity.CargaAcademicaEntity
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.sync.CargaAcademicaSyncManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class CargaAcademicaViewModel(
    private val localRepository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val matricula = SessionManager.matricula

    val carga: StateFlow<List<CargaAcademicaEntity>> =
        localRepository.obtenerCargaAcademica(matricula)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val ultimaActualizacion: StateFlow<Long?> =
        localRepository.obtenerUltimaActualizacionCargaFlow(matricula)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun sincronizar() {
        CargaAcademicaSyncManager.sincronizar(workManager)
    }
}