package com.erick.autenticacinyconsulta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erick.autenticacinyconsulta.ui.navigation.AppScaffold
import com.erick.autenticacinyconsulta.ui.navigation.Routes
import com.erick.autenticacinyconsulta.ui.theme.AutenticaciónYConsultaTheme
import com.erick.autenticacinyconsulta.ui.theme.Screen.CargaAcademicaScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.LoginScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.PerfilScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as SicenetApp).container

        setContent {
            AutenticaciónYConsultaTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.LOGIN
                ) {


                    composable(Routes.LOGIN) {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate(Routes.PERFIL) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            },
                            snRepository = appContainer.networkSNRepository,
                            localRepository = appContainer.localSNRepository
                        )
                    }

                    composable(Routes.PERFIL) {
                        AppScaffold(navController) {
                            PerfilScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    composable(Routes.CARGA) {
                        AppScaffold(navController) {
                            CargaAcademicaScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }
                }
            }
        }
    }
}