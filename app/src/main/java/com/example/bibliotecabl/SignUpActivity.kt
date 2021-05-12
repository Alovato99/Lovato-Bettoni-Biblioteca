package com.example.bibliotecabl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.emailEditText
import kotlinx.android.synthetic.main.activity_signup.passwordEditText


class SignUpActivity : AppCompatActivity() {

    // Public field checker instance
    val checker = fieldChecker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
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
    fun checkSignUp(v: View?)
    {
        // ### CHECKING LOGIN CREDENTIALS ###
        val email: String = emailEditText.getText().toString()
        if(!checker.isValidEmail(email))
        {
            emailEditText.setError(getString(R.string.invalidEmail))
        }


        val pwd: String = passwordEditText.getText().toString()
        if(!checker.isValidPassword(pwd))
        {
            passwordEditText.setError(getString(R.string.invalidPwd))
        }

        val pwdRepeat: String = repeatPasswordEditText.getText().toString()
        if(!checker.isEqualPassword(pwd, pwdRepeat))
        {
            repeatPasswordEditText.setError(getString(R.string.pwdNotMatch))
        }


        val name: String = nameEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(name))
        {
            nameEditText.setError(getString(R.string.invalidName))
        }
        val surnamename: String = nameEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(surnamename))
        {
            surnameEditText.setError(getString(R.string.invalidSurname))
        }

    }
}