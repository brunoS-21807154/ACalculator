package com.example.acalculator

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*
import kotlin.collections.ArrayList

const val EXTRA_HISTORY = "com.example.calculator.HISTORY"

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val VISOR_KEY = "visor"
    private val HISTORY_KEY = "history"

    var operations = mutableListOf(
        Operation("1+1", 2.0),
        Operation("2+3", 5.0),
        Operation("1+1", 2.0),
        Operation("2+3", 5.0),
        Operation("1+1", 2.0),
        Operation("2+3", 5.0),
        Operation("1+1", 2.0)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text_visor.text = savedInstanceState.getString(VISOR_KEY)

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            list_historic?.layoutManager = LinearLayoutManager(this)
            list_historic?.adapter =
                HistoryAdapter(this, R.layout.item_expression, operations)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run { putString(VISOR_KEY, text_visor.text.toString()) }
        outState.run { putParcelableArrayList(HISTORY_KEY, operations as ArrayList<Operation>) }
        super.onSaveInstanceState(outState)
    }

     fun onClickClearLast(view: View) {
        methodToast()
        if (text_visor.length() > 1) {
            text_visor.text = text_visor.text.substring(0, text_visor.length() - 1)
        }
    }

     fun onClickClear(view: View) {
        methodToast()
        text_visor.text = "0"
    }

    fun onClickNumberSymbol(view: View) {
        val symbol = view.tag.toString()
        methodToast()
        Log.i(TAG, "Click no botão $symbol")
        if (text_visor.text == "0") {
            text_visor.text = symbol
        } else {
            text_visor.append(symbol)
        }
    }

    fun onClickArithmeticSymbol(view: View) {
        val symbol = view.tag.toString()
        methodToast()
        Log.i(TAG, "Click no botão $symbol")
        text_visor.append(symbol)

    }

     fun onClickEquals(view: View) {
        methodToast()
        var full_operation = text_visor.text.toString()
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            last_expression.text = text_visor.text
        }

        Log.i(TAG, "Click no botão =")
        val expression = ExpressionBuilder(text_visor.text.toString()).build()

        var result = expression.evaluate().toString()



        if (result[result.length - 1] == '0' && result[result.length - 2] == '.') {
            result = result.substring(0, result.length - 2)
        }

        val operation = Operation(full_operation, result.toDouble())
        operations.add(operation)

        list_historic?.adapter =
            HistoryAdapter(this, R.layout.item_expression, operations)

        text_visor.text = result
        Log.i(TAG, "O resultado da expressão é ${text_visor.text}")
    }

    private fun methodToast() {
        val gCal = GregorianCalendar();
        val time =
            "${gCal.get((Calendar.HOUR_OF_DAY))}:${gCal.get((Calendar.MINUTE))}:${gCal.get((Calendar.SECOND))}"
        val methodName = Thread.currentThread().stackTrace[3].methodName;

        Toast.makeText(this, "Método: $methodName, $time", Toast.LENGTH_SHORT).show()
    }

    fun onClickHistory(view: View){
        val intent = Intent(this, HistoryActivity::class.java)
        intent.apply { putParcelableArrayListExtra(EXTRA_HISTORY, ArrayList(operations)) }
        startActivity(intent)
        finish()
    }
}