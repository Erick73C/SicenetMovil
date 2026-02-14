package com.erick.autenticacinyconsulta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erick.autenticacinyconsulta.di.AppContainer
import com.erick.autenticacinyconsulta.ui.theme.Screen.CalificacionesScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.CardexScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.CargaAcademicaScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.LoginScreen
import com.erick.autenticacinyconsulta.ui.theme.Screen.PerfilScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    appContainer: AppContainer
) {
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
            PerfilScreen(
                localRepository = appContainer.localSNRepository
            )
        }

        composable(Routes.CARGA) {
            CargaAcademicaScreen(
                localRepository = appContainer.localSNRepository
            )
        }

        composable(Routes.CALIFICACIONES) {
            CalificacionesScreen(
                localRepository = appContainer.localSNRepository
            )
        }

        composable(Routes.CARDEX) {
            CardexScreen(
                localRepository = appContainer.localSNRepository
            )
        }
    }
}
