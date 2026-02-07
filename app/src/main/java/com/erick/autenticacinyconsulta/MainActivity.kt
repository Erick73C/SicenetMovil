package com.erick.autenticacinyconsulta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.erick.autenticacinyconsulta.ui.theme.AutenticaciónYConsultaTheme
import com.erick.autenticacinyconsulta.ui.theme.Screen.LoginScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.PerfilScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as SicenetApp).container

        setContent {
            AutenticaciónYConsultaTheme {

                var pantallaActual by remember { mutableStateOf("login") }

                when (pantallaActual) {

                    "login" -> {
                        LoginScreen(
                            onLoginSuccess = { pantallaActual = "perfil" },
                            snRepository = appContainer.networkSNRepository
                        )
                    }

                    "perfil" -> {
                        PerfilScreen(
                            snRepository = appContainer.networkSNRepository
                        )
                    }
                }
            }
        }
    }
}