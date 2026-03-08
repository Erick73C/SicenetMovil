package com.erick.autenticacinyconsulta

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erick.autenticacinyconsulta.ui.navigation.AppScaffold
import com.erick.autenticacinyconsulta.ui.navigation.Routes
import com.erick.autenticacinyconsulta.ui.theme.AutenticaciónYConsultaTheme
import com.erick.autenticacinyconsulta.ui.Screen.*
class MainActivity : ComponentActivity() {

    /*
    Este es el codigo de prueba para saber si el content provider funciona correctamente, habilitar para saber si funciona el provider
    private fun probarProvider() {

        val uri = Uri.parse("content://com.example.sicenet.provider/carga_academica")

        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        if (cursor == null) {
            Log.d("PROVIDER_TEST", "Cursor es null")
            return
        }

        Log.d("PROVIDER_TEST", "Filas encontradas: ${cursor.count}")

        while (cursor.moveToNext()) {

            val materia =
                cursor.getString(cursor.getColumnIndexOrThrow("nombreMateria"))

            val grupo =
                cursor.getString(cursor.getColumnIndexOrThrow("grupo"))

            Log.d("PROVIDER_TEST", "Materia: $materia Grupo: $grupo")
        }

        cursor.close()
    }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Metodo que llama al codigo para saber si sirve el content provide
        //probarProvider()
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

                        //val matriculaUsuario =   // o donde la estés guardando

                        AppScaffold(navController) {
                            PerfilScreen(
                                matricula = SessionManager.matricula,
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