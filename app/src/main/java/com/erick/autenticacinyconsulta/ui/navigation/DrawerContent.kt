package com.erick.autenticacinyconsulta.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerContent(
    onNavigate: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Menú",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Divider()

        DrawerItem("Perfil") { onNavigate(Routes.PERFIL) }
        DrawerItem("Carga Académica") { onNavigate(Routes.CARGA) }
        DrawerItem("Cardex") { onNavigate(Routes.CARDEX) }
        DrawerItem("Calificaciones") { onNavigate(Routes.CALIFICACIONES) }
    }
}

@Composable
fun DrawerItem(
    texto: String,
    onClick: () -> Unit
) {
    Text(
        text = texto,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        fontSize = 16.sp
    )
}
