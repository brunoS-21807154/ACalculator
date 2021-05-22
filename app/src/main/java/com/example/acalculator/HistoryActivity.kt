package com.example.acalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.list_historic

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }

    override fun onStart() {
        super.onStart()

        val operations =
            intent.getParcelableArrayListExtra<Operation>(EXTRA_HISTORY)
        list_historic_history.layoutManager = LinearLayoutManager(this)
        list_historic_history.adapter =
            operations?.let { HistoryAdapter(this, R.layout.item_expression, it) }

    }
}
