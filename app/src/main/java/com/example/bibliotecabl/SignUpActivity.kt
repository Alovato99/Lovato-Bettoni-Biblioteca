package com.example.bibliotecabl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.emailEditText
import kotlinx.android.synthetic.main.activity_signup.passwordEditText


class SignUpActivity : AppCompatActivity() {

    // Public field checker instance
    val checker = fieldChecker()

    // #### FIREBASE AUTH ####
    private lateinit var auth: FirebaseAuth

    private var TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth
        loginTextView.setOnClickListener {
            onLoginClick()
        }
    }


    private fun onSignUp() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val name = nameEditText.text.toString().trim()
        val surName = surnameEditText.text.toString().trim()
        createUser(name, email, password)

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
        var error: Boolean = false
        if(!checker.isValidEmail(email))
        {
            emailEditText.setError(getString(R.string.invalidEmail))
            error = true
        }


        val pwd: String = passwordEditText.getText().toString()
        if(!checker.isValidPassword(pwd))
        {
            passwordEditText.setError(getString(R.string.invalidPwd))
            error = true
        }

        val pwdRepeat: String = repeatPasswordEditText.getText().toString()
        if(!checker.isEqualPassword(pwd, pwdRepeat))
        {
            repeatPasswordEditText.setError(getString(R.string.pwdNotMatch))
            error = true
        }


        val name: String = nameEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(name))
        {
            nameEditText.setError(getString(R.string.invalidName))
            error = true
        }
        val surnamename: String = nameEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(surnamename))
        {
            surnameEditText.setError(getString(R.string.invalidSurname))
            error = true
        }

        if(error)
            return
        onSignUp()

    }



    private fun createUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val currenyUser = auth.currentUser
                    val uid = currenyUser!!.uid
                    val userMap = HashMap<String, String>()
                    userMap["name"] = userName
                    val database = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                    database.setValue(userMap).addOnCompleteListener { task ->
                        /*if (task.isSuccessful) {
                               // val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }*/
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }
}