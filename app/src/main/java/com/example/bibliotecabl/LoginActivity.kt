package com.example.bibliotecabl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEditText
import kotlinx.android.synthetic.main.activity_login.passwordEditText
import kotlinx.android.synthetic.main.activity_signup.*

// ### TEST IMPORT ###
//import kotlinx.android.synthetic.main.activity_signup.*
//import kotlinx.android.synthetic.main.activity_signup.emailEditText
//import kotlinx.android.synthetic.main.activity_signup.passwordEditText
//import kotlinx.android.synthetic.main.activity_test.*

class LoginActivity : AppCompatActivity() {

    // Public field checker instance
    val checker = fieldChecker()

    private lateinit var auth: FirebaseAuth
    private var TAG = "SignUpActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseAuth
        auth = Firebase.auth

        setContentView(R.layout.activity_login)
        emailEditText.setText("aaaa@aaa.aa")
        passwordEditText.setText("aaaaaa")

        signUpTextView.setOnClickListener {
            onSignUpClick()
        }
        forgottenPasswordTextView.setOnClickListener {
            onForgottenPasswordClick()
        }
        //start application commit test
    }
    private fun onSignUpClick() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun onForgottenPasswordClick()
    {
        val intent = Intent(this, ForgottenPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun checkLogin(v: View?)
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
        onLogin()

    }
    private fun onLogin()
    {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        loginUser(email,password)
    }
    private fun loginUser(email: String, password: String)
    {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password) .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, R.string.authenticationFailed,
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}