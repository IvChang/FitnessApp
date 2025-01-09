package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    var et_emailLogin: EditText? = null
    var et_pwdLogin: EditText? = null
    var btn_login: Button? = null
    var btn_toRegister: Button? = null

    var dbAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        et_emailLogin = findViewById(R.id.et_emailLogin)
        et_pwdLogin = findViewById(R.id.et_pwdLogin)
        btn_login = findViewById(R.id.btn_login)
        btn_toRegister = findViewById(R.id.btn_toRegister)

        dbAuth = FirebaseAuth.getInstance()
        user = dbAuth!!.currentUser

        // si on trouve une instance connectée d'un utilisateur, on peut passer immédiatemment à MainActivity
        if (user != null) {
            Log.d("test1", "user is already connected")
            val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }

        btn_login!!.setOnClickListener {v: View? ->

            val email = et_emailLogin!!.text.toString()
            val pwd = et_pwdLogin!!.text.toString()

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && pwd.length >= 5) {

                dbAuth!!.signInWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("test1", "Logged in successfully")
                        var user: FirebaseUser? = dbAuth!!.currentUser

                        val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(mainActivity)
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_emailLogin!!.setError("Invalid email format")
                et_emailLogin!!.requestFocus()
            } else {
                et_pwdLogin!!.setError("Minimum password length is 5")
                et_pwdLogin!!.requestFocus()
            }

        }

        btn_toRegister!!.setOnClickListener {v: View? ->
            val registerActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(registerActivity)
            finish()
        }


    }
}