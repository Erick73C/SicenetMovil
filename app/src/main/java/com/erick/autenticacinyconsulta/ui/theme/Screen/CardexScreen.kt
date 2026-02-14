package com.erick.autenticacinyconsulta.ui.theme.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erick.autenticacinyconsulta.ViewModel.CardexViewModel
import com.erick.autenticacinyconsulta.ViewModel.CardexViewModelFactory
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import java.util.Date

@Composable
fun CardexScreen(
    localRepository: LocalSNRepository
) {
    val context = LocalContext.current

    val viewModel: CardexViewModel = viewModel<CardexViewModel>(
        factory = CardexViewModelFactory(
            localRepository = localRepository,
            context = context
        )
    )

    LaunchedEffect(Unit) {
        viewModel.sincronizar()
    }

    val cardex by viewModel.cardex.collectAsState()
    val ultimaActualizacion by viewModel.ultimaActualizacion.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Cárdex Académico",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        ultimaActualizacion?.let {
            Text(
                text = "Última actualización: ${Date(it)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cardex.isEmpty()) {
            Text("No hay información disponible")
        } else {
            LazyColumn {
                items(cardex) { materia ->
                    CardexItem(materia)
                }
            }
        }
    }
}
