package com.erick.autenticacinyconsulta.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.repository.SNRepository

class PerfilViewModelFactory(
    private val localRepository: LocalSNRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {

            val workManager = WorkManager.getInstance(context)

            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel(
                localRepository,
                workManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}