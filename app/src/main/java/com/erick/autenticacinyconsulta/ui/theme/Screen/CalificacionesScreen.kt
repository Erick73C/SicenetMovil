package com.erick.autenticacinyconsulta.ui.theme.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erick.autenticacinyconsulta.ViewModel.CalificacionesViewModel
import com.erick.autenticacinyconsulta.ViewModel.CalificacionesViewModelFactory
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionFinalEntity
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionUnidadEntity
import com.erick.autenticacinyconsulta.data.repository.LocalSNRepository

@Composable
fun CalificacionesScreen(
    localRepository: LocalSNRepository
) {
    val viewModel: CalificacionesViewModel = viewModel(
        factory = CalificacionesViewModelFactory(localRepository)
    )

    val finales by viewModel.calificacionesFinales.collectAsState()
    val unidades by viewModel.calificacionesUnidad.collectAsState()

    val greenPrimary = Color(0xFF2E7D32)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Reporte de Calificaciones",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = greenPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (finales.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator(color = greenPrimary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    TableHeader()
                }

                items(finales) { materiaFinal ->
                    val unidadesMateria = unidades.filter { 
                        it.materia.equals(materiaFinal.materia, ignoreCase = true) 
                    }
                    CalificacionRow(materiaFinal, unidadesMateria)
                }
            }
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9))
            .padding(8.dp)
            .border(1.dp, Color.LightGray)
    ) {
        Text(
            text = "Materia",
            modifier = Modifier.weight(2f),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = "Final",
            modifier = Modifier.weight(0.7f),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// fila individual de la tabla que representa una materia
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalificacionRow(
    materiaFinal: CalificacionFinalEntity,
    unidades: List<CalificacionUnidadEntity>
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                // Nombre de la Materia
                Text(
                    text = materiaFinal.materia,
                    modifier = Modifier.weight(2f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                // Calificación Final
                Box(
                    modifier = Modifier.weight(0.7f),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Surface(
                        color = if (materiaFinal.calificacionFinal >= 70) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (materiaFinal.calificacionFinal == 0) "-" else "${materiaFinal.calificacionFinal}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold,
                            color = if (materiaFinal.calificacionFinal >= 70) Color(0xFF2E7D32) else Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 0.5.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            // parte para las
            Text(
                text = "Detalle por Unidades:",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            if (unidades.isEmpty() || (unidades.size == 1 && unidades[0].calificacion == 0)) {
                Text(
                    text = "Aún no se han capturado evaluaciones.",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            } else {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    unidades.forEach { unidad ->
                        Badge(
                            containerColor = if (unidad.calificacion >= 70) Color(0xFFC8E6C9) else Color(0xFFFFCDD2),
                            contentColor = Color.DarkGray,
                            modifier = Modifier.height(24.dp)
                        ) {
                            Text(
                                text = "U${unidad.unidad}: ${unidad.calificacion}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
