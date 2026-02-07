package com.erick.autenticacinyconsulta.ui.theme.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erick.autenticacinyconsulta.ViewModel.LoginViewModel
import com.erick.autenticacinyconsulta.ViewModel.LoginViewModelFactory
import com.erick.autenticacinyconsulta.data.repository.SNRepository

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    snRepository: SNRepository
) {
    val context = LocalContext.current

    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            context = context,
            snRepository = snRepository
        )
    )

    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Colores verde personalizados
    val greenPrimary = Color(0xFF2E7D32)
    val greenLight = Color(0xFF4CAF50)
    val greenDark = Color(0xFF1B5E20)
    val greenAccent = Color(0xFF66BB6A)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card contenedor con elevación
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Título con estilo mejorado
                    Text(
                        text = "SICENET",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = greenPrimary,
                        fontSize = 42.sp,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text = "Bienvenido",
                        style = MaterialTheme.typography.bodyLarge,
                        color = greenPrimary.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Campo de usuario con icono
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = greenPrimary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = greenPrimary,
                            unfocusedBorderColor = greenLight.copy(alpha = 0.5f),
                            focusedLabelColor = greenPrimary,
                            cursorColor = greenPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de contraseña con icono
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = greenPrimary
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = greenPrimary,
                            unfocusedBorderColor = greenLight.copy(alpha = 0.5f),
                            focusedLabelColor = greenPrimary,
                            cursorColor = greenPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = {
                            viewModel.login(
                                usuario = usuario,
                                password = password,
                                onSuccess = onLoginSuccess,
                                onError = { mensaje ->
                                    Log.e("SICENET_LOGIN", mensaje)
                                }
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = greenPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
