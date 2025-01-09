package com.example.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    var et_emailRegister: EditText? = null
    var et_usernameRegister: EditText? = null
    var et_pwdRegister: EditText? = null
    var et_confirmPwdRegister: EditText? = null
    var btn_register: Button? = null
    var btn_toLogin: Button? = null

    var dbAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        et_emailRegister = findViewById(R.id.et_emailRegister)
        et_usernameRegister = findViewById(R.id.et_usernameRegister)
        et_pwdRegister = findViewById(R.id.et_pwdRegister)
        et_confirmPwdRegister = findViewById(R.id.et_confirmPwdRegister)
        btn_register = findViewById(R.id.btn_register)
        btn_toLogin = findViewById(R.id.btn_toLogin)

        dbAuth = FirebaseAuth.getInstance()

        btn_register!!.setOnClickListener {v: View? ->



            if (et_pwdRegister!!.text.toString().equals(et_confirmPwdRegister!!.text.toString())) {
                val email = et_emailRegister!!.text.toString()
                val pwd = et_pwdRegister!!.text.toString()
                val username = et_usernameRegister!!.text.toString()

                if (username.length > 0) {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                        if (pwd.length >= 5) {

                            var imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                            dbAuth!!.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("test1", "Registered successfully")
                                    var user: FirebaseUser? = dbAuth!!.currentUser

                                    var profile: UserProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(username).build()
                                    user!!.updateProfile(profile)
                                    Log.d("test1", "Registered successfully as ${profile.displayName}")

                                    val mainActivity = Intent(this@RegisterActivity, MainActivity::class.java)
                                    startActivity(mainActivity)
                                    finish()

                                } else {
                                    et_emailRegister!!.setError("This email already exists. Please enter another one")
                                    et_emailRegister!!.requestFocus()
                                }
                            }

                        } else {
                            et_pwdRegister!!.setError("Minimum password length is 5")
                            et_pwdRegister!!.requestFocus()
                        }

                    } else {
                        et_emailRegister!!.setError("Invalid email format")
                        et_emailRegister!!.requestFocus()
                    }
                } else {
                    et_usernameRegister!!.setError("Please enter your username")
                    et_usernameRegister!!.requestFocus()
                }


            } else {
                et_confirmPwdRegister!!.setError("Given passwords do not match")
                et_confirmPwdRegister!!.requestFocus()
            }

        }

        btn_toLogin!!.setOnClickListener {v: View? ->
            val loginActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(loginActivity)
            finish()
        }



    }
}