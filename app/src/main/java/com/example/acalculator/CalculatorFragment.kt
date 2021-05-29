package com.example.acalculator

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional

import kotlinx.android.synthetic.main.fragment_calculator.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

private const val INIT_OPERATIONS = "initOperations"

class CalculatorFragment : Fragment() {

    private val TAG = MainActivity::class.java.simpleName

    var operations = mutableListOf<Operation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operations = it.getParcelableArrayList<Operation>(INIT_OPERATIONS) as MutableList<Operation>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onStart() {
        super.onStart()

        list_historic?.layoutManager = LinearLayoutManager(activity as Context)
        list_historic?.adapter = HistoryAdapter(activity as Context, R.layout.item_expression, operations)
    }

    @OnClick(R.id.button_backspace)
    fun onClickClearLast() {
        methodToast()
        if (text_visor.length() > 1) {
            text_visor.text = text_visor.text.substring(0, text_visor.length() - 1)
        }
    }

    @OnClick(R.id.button_clear)
    fun onClickClear() {
        methodToast()
        text_visor.text = "0"
    }

    @Optional
    @OnClick(
        R.id.button_0,
        R.id.button_00,
        R.id.button_1,
        R.id.button_2,
        R.id.button_3,
        R.id.button_4,
        R.id.button_5,
        R.id.button_6,
        R.id.button_7,
        R.id.button_8,
        R.id.button_9
    )
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

    private fun methodToast() {
        val gCal = GregorianCalendar();
        val time =
            "${gCal.get((Calendar.HOUR_OF_DAY))}:${gCal.get((Calendar.MINUTE))}:${gCal.get((Calendar.SECOND))}"
        val methodName = Thread.currentThread().stackTrace[3].methodName;

    }

    @OnClick(
        R.id.button_addition,
        R.id.button_subtraction,
        R.id.button_multiplication,
        R.id.button_division,
        R.id.button_dot
    )
    fun onClickArithmeticSymbol(view: View) {
        val symbol = view.tag.toString()
        methodToast()
        Log.i(TAG, "Click no botão $symbol")
        text_visor.append(symbol)

    }

    @OnClick(R.id.button_equals)
    fun onClickEquals() {
        methodToast()
        var fullOperation = text_visor.text.toString()
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            lastExpression.text = text_visor.text
        }

        Log.i(TAG, "Click no botão =")
        val expression = ExpressionBuilder(text_visor.text.toString()).build()

        var result = expression.evaluate().toString()

        if (result[result.length - 1] == '0' && result[result.length - 2] == '.') {
            result = result.substring(0, result.length - 2)
        }

        val operation = Operation(fullOperation, result.toDouble())
        operations.add(operation)

        list_historic?.adapter =
            HistoryAdapter(activity as Context, R.layout.item_expression, operations)

        text_visor.text = result
        Log.i(TAG, "O resultado da expressão é ${text_visor.text}")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 init_operations.
         * @return A new instance of fragment HistoryFragment.
         */
        @JvmStatic
        fun newInstance(operations: MutableList<Operation>?): CalculatorFragment {
            val args = Bundle()
            args.putParcelableArrayList(INIT_OPERATIONS, operations as ArrayList<out Parcelable>)
            val fragment = CalculatorFragment()
            fragment.arguments = args
            return fragment
        }
    }

}