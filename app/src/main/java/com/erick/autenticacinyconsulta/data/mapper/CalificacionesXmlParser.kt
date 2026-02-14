package com.erick.autenticacinyconsulta.data.mapper
// cris
import android.util.Log
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionFinalEntity
import com.erick.autenticacinyconsulta.data.local.entity.CalificacionUnidadEntity
import org.json.JSONObject

object CalificacionesXmlParser {
    data class Resultado(
        val finales: List<CalificacionFinalEntity>,
        val unidades: List<CalificacionUnidadEntity>
    )

    fun parse(xml: String): Resultado {
        val finales = mutableListOf<CalificacionFinalEntity>()
        val unidades = mutableListOf<CalificacionUnidadEntity>()

        Log.d("CALIF_PARSE", "Iniciando parseo de XML. Tamaño: ${xml.length}")

        // Búsqueda más flexible del contenido
        val contenido = try {
            if (xml.contains("<getCalificacionesByAlumnoResult>")) {
                xml.substringAfter("<getCalificacionesByAlumnoResult>")
                    .substringBefore("</getCalificacionesByAlumnoResult>")
            } else {
                Log.e("CALIF_PARSE", "No se encontró el tag <getCalificacionesByAlumnoResult>")
                return Resultado(finales, unidades)
            }
        } catch (e: Exception) {
            Log.e("CALIF_PARSE", "Error extrayendo contenido del XML", e)
            return Resultado(finales, unidades)
        }

        val jsonLimpio = contenido
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&amp;", "&")
            .trim()

        Log.d("CALIF_PARSE_RAW", "JSON extraído: $jsonLimpio")

        if (jsonLimpio.isBlank() || jsonLimpio == "[]" || jsonLimpio == "{}") {
            Log.w("CALIF_PARSE", "El contenido JSON está vacío")
            return Resultado(finales, unidades)
        }

        try {
            val jsonObject = JSONObject(jsonLimpio)
            val jsonArray = jsonObject.optJSONArray("lstCalif")
            
            if (jsonArray == null) {
                Log.e("CALIF_PARSE", "No se encontró el array 'lstCalif' en el JSON")
                return Resultado(finales, unidades)
            }

            Log.d("CALIF_PARSE", "Se encontraron ${jsonArray.length()} materias")
            val timestamp = System.currentTimeMillis()

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val materia = item.optString("Materia", "Desconocida")

                // Calificación final
                val califFinal = item.optInt("Calif", -1)
                val acred = item.optString("Acred", "")

                finales.add(
                    CalificacionFinalEntity(
                        materia = materia,
                        calificacionFinal = if (califFinal == -1) 0 else califFinal,
                        acreditado = acred,
                        ultimaActualizacion = timestamp
                    )
                )

                // Calificaciones por unidad (C1 a C13)
                for (u in 1..13) {
                    val key = "C$u"
                    if (item.has(key)) {
                        val califVal = item.optString(key, "")
                        if (califVal.isNotBlank() && califVal != "null") {
                            val califInt = try { califVal.toInt() } catch(e: Exception) { -1 }
                            if (califInt != -1) {
                                unidades.add(
                                    CalificacionUnidadEntity(
                                        materia = materia,
                                        unidad = u,
                                        calificacion = califInt,
                                        ultimaActualizacion = timestamp
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Log.d("CALIF_PARSE_SUCCESS", "Parseo completado: ${finales.size} finales, ${unidades.size} unidades")

        } catch (e: Exception) {
            Log.e("CALIF_PARSE_ERROR", "Error parseando JSON de calificaciones", e)
        }

        return Resultado(finales, unidades)
    }
}