package com.example.bibliotecabl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.emailEditText

class ForgottenPasswordActivity : AppCompatActivity() {
    // Public field checker instance
    val checker = fieldChecker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgottenpassword)
        loginTextView.setOnClickListener {
            onLoginClick()
        }
    }
    fun onLoginClick()
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun checkEmail(v: View?)
    {
        // ### CHECKING LOGIN CREDENTIALS ###
        val email: String = emailEditText.getText().toString()
        if(!checker.isValidEmail(email))
        {
            emailEditText.setError(getString(R.string.invalidEmail))
        }
    }
}