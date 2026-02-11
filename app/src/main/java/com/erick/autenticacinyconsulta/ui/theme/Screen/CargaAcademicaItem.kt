package com.erick.autenticacinyconsulta.ui.theme.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erick.autenticacinyconsulta.data.local.entity.CargaAcademicaEntity

@Composable
fun CargaAcademicaItem(materia: CargaAcademicaEntity) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = materia.nombreMateria,
                fontWeight = FontWeight.Bold
            )

            Text("Clave: ${materia.claveMateria}")
            Text("Grupo: ${materia.grupo}")
            Text("Docente: ${materia.docente}")
            Text("Créditos: ${materia.creditos}")
            Text("Horario: ${materia.horario}")
        }
    }
}
