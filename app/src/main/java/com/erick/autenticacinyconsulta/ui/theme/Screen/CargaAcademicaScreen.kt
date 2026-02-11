package com.erick.autenticacinyconsulta.ui.theme.Screen

import android.util.Log
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erick.autenticacinyconsulta.ViewModel.CargaAcademicaViewModel
import com.erick.autenticacinyconsulta.ViewModel.CargaAcademicaViewModelFactory
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository
import java.util.Date

@Composable
fun CargaAcademicaScreen(
    localRepository: LocalSNRepository
) {
    val context = LocalContext.current

    val viewModel: CargaAcademicaViewModel = viewModel(
        factory = CargaAcademicaViewModelFactory(
            localRepository = localRepository,
            context = context
        )
    )

    LaunchedEffect(Unit) {

        viewModel.sincronizar()
    }

    val carga by viewModel.carga.collectAsState()
    val ultimaActualizacion by viewModel.ultimaActualizacion.collectAsState()


    LaunchedEffect(carga) {
        Log.d("UI_DEBUG", "Lista en UI: ${carga.size}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Carga Académica",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        ultimaActualizacion?.let {
            Text(
                text = "Última actualización: ${Date(it)}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (carga.isEmpty()) {
            Text("No hay carga académica disponible")
        } else {
            LazyColumn {
                items(carga) { materia ->
                    CargaAcademicaItem(materia)
                }
            }
        }
    }
}

