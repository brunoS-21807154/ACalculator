package com.example.acalculator

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*
import kotlin.collections.ArrayList

const val EXTRA_HISTORY = "com.example.acalculator.HISTORY"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG = MainActivity::class.java.simpleName



    private val VISOR_KEY = "visor"
    private val HISTORY_KEY = "history"

    private var operations = mutableListOf<Operation>(
        Operation("1+1", 2.0),
        Operation("2+3", 5.0),
        Operation("1+1", 2.0),
        Operation("2+3", 5.0),
        Operation("1+1", 2.0),
        Operation("2+3", 5.0),
        Operation("1+1", 2.0),
        Operation("2+3", 5.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupDrawerMenu()
        if(!screenRotated(savedInstanceState)){
            NavigationManager.goToCalculatorFragment(supportFragmentManager, operations)
        }
        NavigationManager.goToCalculatorFragment(supportFragmentManager, operations)
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean{
        return savedInstanceState != null
    }

    private fun setupDrawerMenu(){
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
        R.string.drawer_open, R.string.drawer_close)
        nav_drawer.setNavigationItemSelectedListener(this)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

     override fun onNavigationItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.nav_calculator ->
                NavigationManager.goToCalculatorFragment(supportFragmentManager, operations)
            R.id.nav_history ->
                NavigationManager.goToHistoryFragment(supportFragmentManager, operations)
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        } else if(supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else{
            super.onBackPressed()
        }
    }
}