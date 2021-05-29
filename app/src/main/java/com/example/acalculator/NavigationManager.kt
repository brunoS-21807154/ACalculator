package com.example.acalculator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class NavigationManager {

    companion object{
        private fun placeFragment(fm: FragmentManager, fragment: Fragment){
            val transition = fm.beginTransaction()
            transition.replace(R.id.frame, fragment)
            transition.addToBackStack(null)
            transition.commit()
        }

        fun goToCalculatorFragment(fm: FragmentManager, operations: MutableList<Operation>?){
            val calculatorFragment = CalculatorFragment.newInstance(operations)
            placeFragment(fm, calculatorFragment)
        }

        fun goToHistoryFragment(fm: FragmentManager, operations: MutableList<Operation>?){
            val historyFragment = HistoryFragment.newInstance(operations)
            placeFragment(fm, historyFragment)
        }
    }


}