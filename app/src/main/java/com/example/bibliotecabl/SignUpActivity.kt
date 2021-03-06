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
        val name = bookTitleEditText.text.toString()
        val surname = bookAuthorEditText.text.toString()
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


        val name: String = bookTitleEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(name))
        {
            bookTitleEditText.setError(getString(R.string.invalidName))
            error = true
        }
        val surname: String = bookTitleEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(surname))
        {
            bookAuthorEditText.setError(getString(R.string.invalidSurname))
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
                    val currentUser = auth.currentUser
                    val uid = currentUser!!.uid
                    val database = FirebaseDatabase.getInstance().getReference("Users")
                    /*userMap["name"] = name
                    database.setValue(userMap)
                    userMap["surname"] = surname
                    database.setValue(userMap)*/
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
                    /*val database2 = FirebaseDatabase.getInstance().getReference("Users")*/
                    database.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val counter = dataSnapshot.childrenCount.toInt()
                            //userMap["admin"] = (counter==1).toString()
                            val user = User(name, surname, (counter==0))
                            database.child(uid).setValue(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    if(counter==0)
                                    {
                                        val countMap = HashMap<String,Any>()
                                        val bookMap = HashMap<String,Any>()
                                        countMap["count"]=0
                                        FirebaseDatabase.getInstance().getReference("New_Arrivals").setValue(countMap)
                                        for(i in 0..9)
                                        {
                                            bookMap[i.toString()]=""
                                        }
                                        FirebaseDatabase.getInstance().getReference("New_Arrivals").child("BookList").setValue(bookMap)
                                    }
                                    val rentMap=HashMap<String,Any>()
                                    rentMap["active_rents"]=0
                                    FirebaseDatabase.getInstance().getReference("Rented_Books").child(uid).setValue(rentMap)
                                    rentMap["total_rents"]=0
                                    FirebaseDatabase.getInstance().getReference("Rented_Books").child(uid).setValue(rentMap)
                                    val intent = Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })



                    val emailToUidReference = FirebaseDatabase.getInstance().getReference("emailToUID")
                    emailToUidReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            emailToUidReference.child(email.replace(".", ",")).setValue(uid).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("emailToUid", "Added reference email to uid")
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

class emailToUID(val email: String, val uid: String)