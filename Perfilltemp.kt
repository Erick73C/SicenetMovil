package com.erick.autenticacinyconsulta.ui.theme.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erick.autenticacinyconsulta.ViewModel.PerfilViewModel
import com.erick.autenticacinyconsulta.ViewModel.PerfilViewModelFactory
import com.erick.autenticacinyconsulta.data.repository.SNRepository

@Composable
fun PerfilScreenTemp(
    snRepository: SNRepository,
    viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(snRepository)
    )
) {
    LaunchedEffect(Unit) {
        viewModel.cargarPerfil()
    }

    val perfil = viewModel.perfil

    // Colores verde personalizados (mismos que LoginScreen)
    val greenPrimary = Color(0xFF2E7D32)
    val greenLight = Color(0xFF4CAF50)
    val greenDark = Color(0xFF1B5E20)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        greenDark,
                        greenPrimary,
                        greenLight
                    )
                )
            )
    ) {
        if (perfil == null) {
            // Estado de carga
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cargando perfil...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Título de bienvenida
                Text(
                    text = "Perfil del Alumno",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 36.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Tarjeta principal con información del usuario
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        PerfilItem("Nombre", perfil.nombre)
                        
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        
                        PerfilItem("Matrícula", perfil.matricula)
                        
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        
                        PerfilItem("Carrera", perfil.carrera)
                        
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        
                        PerfilItem("Especialidad", perfil.especialidad)
                        
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        
                        PerfilItem("Semestre actual", perfil.semActual.toString())
                        
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        
                        PerfilItem("Créditos acumulados", perfil.cdtosAcumulados.toString())
                        
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        
                        PerfilItem("Estatus", perfil.estatus)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun PerfilItem(titulo: String, valor: String) {
    // Colores verde personalizados
    val greenPrimary = Color(0xFF2E7D32)
    
    Column {
        Text(
            text = titulo,
            fontSize = 14.sp,
            color = greenPrimary.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = valor,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}