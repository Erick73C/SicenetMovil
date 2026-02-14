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
import com.erick.autenticacinyconsulta.ui.theme.Screen.CardexScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.CargaAcademicaScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.LoginScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.PerfilScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.CalificacionesScreen

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

                    composable(Routes.CARDEX) {
                        AppScaffold(navController) {
                            CardexScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    composable(Routes.CALIFICACIONES){
                        AppScaffold(navController) {
                            CalificacionesScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                }
            }
        }
    }
}