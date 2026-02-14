package com.erick.autenticacinyconsulta.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository

class CardexViewModelFactory(
    private val localRepository: LocalSNRepository,
    context: Context
) : ViewModelProvider.Factory {

    private val workManager = WorkManager.getInstance(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CardexViewModel(
            localRepository,
            workManager
        ) as T
    }
}
