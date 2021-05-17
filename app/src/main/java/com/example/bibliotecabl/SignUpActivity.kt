package com.example.bibliotecabl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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
        val surname = surnameEditText.text.toString().trim()
        createUser(name, surname, email, password)

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



    private fun createUser(name: String, surname: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val currenyUser = auth.currentUser
                    val uid = currenyUser!!.uid
                    val userMap = HashMap<String, String>()
                    val database = FirebaseDatabase.getInstance().getReference("Users").child(uid)
                    userMap["name"] = name
                    database.setValue(userMap)
                    userMap["surname"] = surname
                    database.setValue(userMap)

                    /*userMap["admin"]="true"

                   val database2 = FirebaseDatabase.getInstance().getReference("Users")
//You must remember to remove the listener when you finish using it, also to keep track of changes you can use the ChildChange
                    database2.addChildEventListener(object : ChildEventListener {
                        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                            Log.e(dataSnapshot.key, dataSnapshot.childrenCount.toString() + "")
                            userMap["admin"] = (dataSnapshot.childrenCount.toString())
                            database.setValue(userMap).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })*/


                    val database2 = FirebaseDatabase.getInstance().getReference("Users")
                    database2.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val counter = dataSnapshot.childrenCount.toInt()
                            userMap["admin"] = (counter==1).toString()
                            database.setValue(userMap).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })




                    /*database.setValue(userMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                               val intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    }*/
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                            baseContext, R.string.accountAlreadyExists,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }
}