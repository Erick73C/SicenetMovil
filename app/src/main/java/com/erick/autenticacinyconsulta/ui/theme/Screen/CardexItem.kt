package com.erick.autenticacinyconsulta.ui.theme.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erick.autenticacinyconsulta.data.local.entity.CardexEntity

@Composable
fun CardexItem(materia: CardexEntity) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = materia.materia,
                fontWeight = FontWeight.Bold
            )

            Text("Calificación: ${materia.calificacion}")

            Text("Acreditado: ${materia.acreditado}")

            Text(
                text = "Periodo: ${materia.periodo}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
