package com.example.acalculator

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_expression.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val VISOR_KEY = "visor"
    var operation_list = arrayListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()


        val orientation = resources.configuration.orientation

       var listAdapter = HistoryAdapter(this, R.layout.item_expression, operation_list)

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            list_historic.adapter = listAdapter

            list_historic.onItemClickListener = OnItemClickListener { parent, view, position, id ->

                val expression = parent.getItemAtPosition(position).toString()
                val separatedResult = expression.split('=')
                Toast.makeText(this, "Resultado: ${separatedResult[1]}", Toast.LENGTH_SHORT).show()
            }
        }



        button_1.setOnClickListener { onClickNumberSymbol("1"); }
        button_2.setOnClickListener { onClickNumberSymbol("2"); }
        button_3.setOnClickListener { onClickNumberSymbol("3"); }
        button_4.setOnClickListener { onClickNumberSymbol("4"); }
        button_5.setOnClickListener { onClickNumberSymbol("5"); }
        button_6.setOnClickListener { onClickNumberSymbol("6"); }
        button_addition.setOnClickListener { onClickArithmeticSymbol("+"); }
        button_subtraction.setOnClickListener { onClickArithmeticSymbol("-"); }
        button_multiplication.setOnClickListener { onClickArithmeticSymbol("*"); }
        button_division.setOnClickListener { onClickArithmeticSymbol("/"); }
        button_equals.setOnClickListener { onClickEquals(listAdapter); }
        button_clear.setOnClickListener { onClickClear(); }
        button_backspace.setOnClickListener { onClickClearLast(); }



    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        text_visor.text = savedInstanceState.getString(VISOR_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run { putString(VISOR_KEY, text_visor.text.toString()) }
        super.onSaveInstanceState(outState)
    }

    private fun onClickClearLast() {
        methodToast()
        if(text_visor.length() > 1){
            text_visor.text = text_visor.text.substring(0, text_visor.length() - 1)
        }
    }

    private fun onClickClear() {
        methodToast()
        text_visor.text = "0"
    }

    private fun onClickNumberSymbol(symbol: String) {
        methodToast()
        Log.i(TAG, "Click no botão $symbol")
        if (text_visor.text == "0") {
            text_visor.text = symbol
        } else {
            text_visor.append(symbol)
        }
    }

    private fun onClickArithmeticSymbol(symbol: String) {
        methodToast()
        Log.i(TAG, "Click no botão $symbol")
        text_visor.append(symbol)

    }

    private fun onClickEquals(adapter: ArrayAdapter<String>) {
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

        full_operation += "=$result"
        operation_list.add(full_operation)

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            adapter.notifyDataSetChanged()
            list_historic.smoothScrollToPosition(adapter.count-1)
        }

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
}