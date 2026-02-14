package com.erick.autenticacinyconsulta.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.erick.autenticacinyconsulta.data.local.db.SicenetDatabase
import com.erick.autenticacinyconsulta.data.mapper.CardexXmlParser

class SicenetCardexDbWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val xml = inputData.getString("cardex_xml")
            ?: return Result.failure()

        val db = SicenetDatabase.getDatabase(applicationContext)

        val lista = CardexXmlParser.parse(xml)
        db.cardexDao().limpiar()
        db.cardexDao().insertarTodo(lista)
        val total = db.cardexDao().contarTodo()

        //Longs de prueba
//        Log.d("DB_TOTAL", "Total registros en tabla: $total")
//        Log.d("WM_CARDEX_DB", "Cardex guardado (${lista.size})")

        return Result.success()
    }
}

