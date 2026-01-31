package com.erick.autenticacinyconsulta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.erick.autenticacinyconsulta.ViewModel.LoginViewModel
import com.erick.autenticacinyconsulta.data.repository.NetworSNRepository
import com.erick.autenticacinyconsulta.di.DefaultAppContainer
import com.erick.autenticacinyconsulta.ui.theme.AutenticaciónYConsultaTheme
import com.erick.autenticacinyconsulta.ui.theme.Screen.LoginScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.PerfilScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            AutenticaciónYConsultaTheme {

                var pantallaActual by remember { mutableStateOf("login") }
                val appContainer = DefaultAppContainer(applicationContext)
                when (pantallaActual) {
                    "login" -> {
                        LoginScreen(
                            onLoginSuccess = { pantallaActual = "perfil" },
                            snRepository = appContainer.snRepository
                        )
                    }

                    "perfil" -> {
                        PerfilScreen(
                            snRepository = appContainer.snRepository
                        )
                    }
                }
            }
        }
    }
}