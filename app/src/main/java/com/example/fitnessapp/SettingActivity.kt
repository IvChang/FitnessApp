package com.example.fitnessapp

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseUser

class SettingActivity : AppCompatActivity() {
    var actionBar: ActionBar? = null
    var btn_changeTheme: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        btn_changeTheme = findViewById(R.id.btn_changeTheme)

        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        var isNightMode = currentNightMode == AppCompatDelegate.MODE_NIGHT_YES
        Log.d("test1", "isNightMode: $isNightMode")
        if (isNightMode) {
            btn_changeTheme!!.text = "Switch to Light Mode"
        } else {
            btn_changeTheme!!.text = "Switch to Night Mode"
        }
        // TODO: save isNightMode in database
        btn_changeTheme!!.setOnClickListener {v: View? ->

            if (isNightMode) {
                Log.d("test1", "true")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn_changeTheme!!.text = "Switch to Night Mode"
            } else {
                Log.d("test1", "false")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn_changeTheme!!.text = "Switch to Light Mode"
            }
            isNightMode = !isNightMode

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // pour retourner en arriere
        if (item.itemId == android.R.id.home) {
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}