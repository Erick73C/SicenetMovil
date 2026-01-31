package com.erick.autenticacinyconsulta.ui.theme.Screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erick.autenticacinyconsulta.ViewModel.PerfilViewModel
import com.erick.autenticacinyconsulta.ViewModel.PerfilViewModelFactory
import com.erick.autenticacinyconsulta.data.repository.SNRepository

@Composable
fun PerfilScreen(snRepository: SNRepository) {

    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(snRepository)
    )

    LaunchedEffect(Unit) {
        viewModel.cargarPerfil()
    }

    Text("Cargando perfil...")
}