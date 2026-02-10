package com.erick.autenticacinyconsulta.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import com.erick.autenticacinyconsulta.data.repository.SNRepository

class LoginViewModelFactory(
    context: Context,
    private val snRepository: SNRepository,
    private val localRepository: LocalSNRepository
) : ViewModelProvider.Factory {

    private val workManager = WorkManager.getInstance(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                snRepository,
                localRepository,
                workManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
