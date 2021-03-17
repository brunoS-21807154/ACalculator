package com.example.acalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        button_1.setOnClickListener {
            Log.i(TAG, "Click no botão 1")
            if (text_visor.text == "0") {
                text_visor.text = "1"
            } else {
                text_visor.append("1")
            }
        }

        button_2.setOnClickListener {
            Log.i(TAG, "Click no botão 2")
            if (text_visor.text == "0") {
                text_visor.text = "2"
            } else {
                text_visor.append("2")
            }
        }

        button_3.setOnClickListener {
            Log.i(TAG, "Click no botão 3")
            if (text_visor.text == "0") {
                text_visor.text = "3"
            } else {
                text_visor.append("3")
            }
        }

        button_adiction.setOnClickListener{
            Log.i(TAG, "Click no botão +")
            text_visor.append("+")
        }

        button_equals.setOnClickListener{

            last_expression.text = text_visor.text

            Log.i(TAG, "Click no botão =")
            val expression = ExpressionBuilder(text_visor.text.toString()).build()

            var result = expression.evaluate().toString()


            if(result[result.length - 1] == '0' && result[result.length - 2] == '.'){
                result = result.substring(0, result.length - 2)
            }

            text_visor.text = result
            Log.i(TAG, "O resultado da expressão é ${text_visor.text}")
        }

    }
}