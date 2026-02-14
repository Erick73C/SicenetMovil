package com.erick.autenticacinyconsulta.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionFinalEntity
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionUnidadEntity
import com.erick.autenticacinyconsulta.data.local.entity.CargaAcademicaEntity
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CalificacionesViewModel(
    private val repository: LocalSNRepository
) : ViewModel() {

    // Materias inscritas
    private val cargaAcademica = repository.obtenerCargaAcademica()
    
    // Calificaciones guardadas
    private val calificacionesFinalesRaw = repository.obtenerCalificacionesFinales()
    private val calificacionesUnidadRaw = repository.obtenerCalificacionesUnidad()

    // cruza la carga academiica con las calif finales para mostrar las materias
    val calificacionesFinales: StateFlow<List<CalificacionFinalEntity>> =
        combine(cargaAcademica, calificacionesFinalesRaw) { carga, finales ->
            carga.map { materia ->
                // si la materia no tiene calif capturada
                finales.find { it.materia.equals(materia.nombreMateria, ignoreCase = true) }
                    ?: CalificacionFinalEntity(
                        materia = materia.nombreMateria,
                       //se crea un estado NP con 0
                        calificacionFinal = 0,
                        acreditado = "NP",
                        ultimaActualizacion = System.currentTimeMillis()
                    )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // cruza la carga academiica con las calif unidad para mostrar el desglose de cada materia
    val calificacionesUnidad: StateFlow<List<CalificacionUnidadEntity>> =
        combine(cargaAcademica, calificacionesUnidadRaw) { carga, unidades ->
            val listaCompleta = mutableListOf<CalificacionUnidadEntity>()
            carga.forEach { materia ->
                val unidadesMateria = unidades.filter { 
                    it.materia.equals(materia.nombreMateria, ignoreCase = true) 
                }
                
                if (unidadesMateria.isNotEmpty()) {
                    listaCompleta.addAll(unidadesMateria)
                } else {
                    // si no hay unidades capturadas, se muestra al menos la U1 en 0 para visualización
                    listaCompleta.add(
                        CalificacionUnidadEntity(
                            materia = materia.nombreMateria,
                            unidad = 1,
                            calificacion = 0,
                            ultimaActualizacion = System.currentTimeMillis()
                        )
                    )
                }
            }
            listaCompleta
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

class CalificacionesViewModelFactory(
    private val repository: LocalSNRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalificacionesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalificacionesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
