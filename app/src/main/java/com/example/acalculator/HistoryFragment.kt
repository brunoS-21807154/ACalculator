package com.example.acalculator

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import butterknife.Optional
import kotlinx.android.synthetic.main.fragment_calculator.*
import java.util.ArrayList

private const val OPERATIONS = "operations"

class HistoryFragment : Fragment() {
    private var operations = mutableListOf<Operation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operations = it.getParcelableArrayList<Operation>(OPERATIONS) as MutableList<Operation>
        }
    }

    override fun onStart() {
        super.onStart()

        list_historic?.layoutManager = LinearLayoutManager(activity as Context)
        list_historic?.adapter = HistoryAdapter(activity as Context, R.layout.item_expression, operations)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment HistoryFragment.
         */

        @JvmStatic
        fun newInstance(operations: MutableList<Operation>?): HistoryFragment {
            val args = Bundle()
            args.putParcelableArrayList(OPERATIONS, operations as ArrayList<out Parcelable>)
            val fragment = HistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}