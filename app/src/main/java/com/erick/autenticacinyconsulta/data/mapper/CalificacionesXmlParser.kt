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

    fun parse(unidadesXml: String, finalesXml: String): Resultado {
        val finales = mutableListOf<CalificacionFinalEntity>()
        val unidades = mutableListOf<CalificacionUnidadEntity>()

        Log.d("CALIF_PARSE", "Iniciando parseo de XMLs. Unidades: ${unidadesXml.length}, Finales: ${finalesXml.length}")

        // 1. Parsear Finales
        parseFinales(finalesXml, finales)

        // 2. Parsear Unidades
        parseUnidades(unidadesXml, unidades)

        return Resultado(finales, unidades)
    }

    private fun parseFinales(xml: String, list: MutableList<CalificacionFinalEntity>) {
        val contenido = try {
            if (xml.contains("<getAllCalifFinalByAlumnosResult>")) {
                xml.substringAfter("<getAllCalifFinalByAlumnosResult>")
                    .substringBefore("</getAllCalifFinalByAlumnosResult>")
            } else if (xml.contains("<getCalificacionesByAlumnoResult>")) {
                // Fallback por si acaso
                xml.substringAfter("<getCalificacionesByAlumnoResult>")
                    .substringBefore("</getCalificacionesByAlumnoResult>")
            } else {
                Log.e("CALIF_PARSE", "No se encontró el tag de resultados finales")
                return
            }
        } catch (e: Exception) {
            Log.e("CALIF_PARSE", "Error extrayendo finales", e)
            return
        }

        val jsonLimpio = limpiarXml(contenido)
        if (jsonLimpio.isBlank() || jsonLimpio == "[]" || jsonLimpio == "{}") return

        try {
            val jsonObject = JSONObject(jsonLimpio)
            val jsonArray = jsonObject.optJSONArray("lstCalif") ?: return
            val timestamp = System.currentTimeMillis()

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                list.add(
                    CalificacionFinalEntity(
                        materia = item.optString("Materia", "Desconocida"),
                        calificacionFinal = item.optInt("Calif", 0),
                        acreditado = item.optString("Acred", ""),
                        ultimaActualizacion = timestamp
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("CALIF_PARSE", "Error parseando finales", e)
        }
    }

    private fun parseUnidades(xml: String, list: MutableList<CalificacionUnidadEntity>) {
        val contenido = try {
            if (xml.contains("<getCalifUnidadesByAlumnoResult>")) {
                xml.substringAfter("<getCalifUnidadesByAlumnoResult>")
                    .substringBefore("</getCalifUnidadesByAlumnoResult>")
            } else {
                Log.e("CALIF_PARSE", "No se encontró el tag de resultados unidades")
                return
            }
        } catch (e: Exception) {
            Log.e("CALIF_PARSE", "Error extrayendo unidades", e)
            return
        }

        val jsonLimpio = limpiarXml(contenido)
        if (jsonLimpio.isBlank() || jsonLimpio == "[]" || jsonLimpio == "{}") return

        try {
            val jsonObject = JSONObject(jsonLimpio)
            val jsonArray = jsonObject.optJSONArray("lstCalif") ?: return
            val timestamp = System.currentTimeMillis()

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val materia = item.optString("Materia", "Desconocida")

                for (u in 1..13) {
                    val key = "C$u"
                    if (item.has(key)) {
                        val califVal = item.optString(key, "")
                        if (califVal.isNotBlank() && califVal != "null") {
                            val califInt = try { califVal.toInt() } catch(e: Exception) { -1 }
                            if (califInt != -1) {
                                list.add(
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
        } catch (e: Exception) {
            Log.e("CALIF_PARSE", "Error parseando unidades", e)
        }
    }

    private fun limpiarXml(xml: String): String {
        return xml.replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&amp;", "&")
            .trim()
    }

}