package com.example.bibliotecabl.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bibliotecabl.*
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.emailEditText
import kotlinx.android.synthetic.main.activity_signup.bookTitleEditText
import kotlinx.android.synthetic.main.activity_signup.bookAuthorEditText
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    val checker = fieldChecker()
    private var name=""
    private var surname=""
    private var email=""
    private var oldPwd=""
    private var newPwd=""
    private var repeatNewPwd=""
    private var pwdEmpty=true
    private var auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database.reference
    private val currentUser = auth.currentUser
    private val uid = currentUser!!.uid



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textViewName: TextView = root.findViewById(R.id.bookTitleEditText)
        val textViewSurname: TextView = root.findViewById(R.id.bookAuthorEditText)
        val textViewEmail: TextView = root.findViewById(R.id.emailEditText)
        val confirm: Button = root.findViewById(R.id.confirmButton)


        database.child("Users").child(uid).get().addOnSuccessListener {
            name=it.child("name").getValue().toString()
            surname=it.child("surname").getValue().toString()
            textViewName.text=name
            textViewSurname.text=surname
            textViewEmail.text=auth.currentUser?.email.toString()
        }



        confirm.setOnClickListener {
            checkConfirm(it)
        }
        return root
    }

    private fun checkConfirm(v: View?) {
        var error: Boolean = false

        name = bookTitleEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(name))
        {
            bookTitleEditText.setError(getString(R.string.invalidName))
            error = true
        }
        surname = bookAuthorEditText.getText().toString()
        if(checker.isInvalidNameOrSurname(surname))
        {
            bookAuthorEditText.setError(getString(R.string.invalidSurname))
            error = true
        }
        email = emailEditText.getText().toString()
        if(!checker.isValidEmail(email))
        {
            emailEditText.setError(getString(R.string.invalidEmail))
        }
        if(!(oldPasswordEditText.getText().isEmpty()&&newPasswordEditText.getText().isEmpty()&&repeatNewPasswordEditText.getText().isEmpty()))
        {
            pwdEmpty=false
            oldPwd = oldPasswordEditText.getText().toString()


                //CONTROLLO E AGGIORNO PASSWORD DATABASE

                   /* val credentials=EmailAuthProvider.getCredential(currentUser?.email!!,oldPwd)
                    currentUser?.reauthenticate(credentials)?.addOnCompleteListener()
                    {
                        if(it.isSuccessful)
                        {
                            //Cambia password
                            currentUser?.updatePassword(newPwd)?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG,"Password aggiornata")
                                }
                            }

                        }
                        else
                            Log.d(TAG,"Autenticazione fallita")
                    }*/

            val credentials=EmailAuthProvider.getCredential(currentUser?.email!!,oldPwd)
            currentUser?.reauthenticate(credentials)?.addOnCompleteListener()
            {
                if (!it.isSuccessful) {
                    error = true
                    oldPasswordEditText.setError(getString(R.string.invalidPwd))
                }
            }
            newPwd = newPasswordEditText.getText().toString()
            if (!checker.isValidPassword(newPwd)) {
                newPasswordEditText.setError(getString(R.string.invalidPwd))
                error = true
            }
            repeatNewPwd = repeatNewPasswordEditText.getText().toString()
            if (!checker.isEqualPassword(newPwd, repeatNewPwd)) {
                oldPasswordEditText.setError(getString(R.string.invalidPwd))
                error = true
            }
        }
        if(error)
            return
        onConfirm()
    }
    private fun onConfirm()
    {
        name.trim()
        surname.trim()
        email.trim()
        if(!pwdEmpty)
        {
            oldPwd.trim()
            newPwd.trim()
            repeatNewPwd.trim()
        }
        commitChanges()

    }
    private fun commitChanges()
    {
        /*val admin=database.child("Users").child(uid).child("admin").get().toString().trim().toBoolean()
        val user = User(name, surname,admin)
        //Cambio nome
        database.child("Users").child(uid).setValue(user)*/

        val userMap = HashMap<String, Any>()
        //Cambio nome
        userMap["name"]=name
        database.child("Users").child(uid).updateChildren(userMap)

        //Cambio conome
        userMap["surname"]=surname
        database.child("Users").child(uid).updateChildren(userMap)
        //root.homeName.text = "$name $surname"

        //Cambio email
        currentUser?.updateEmail(email)

        //Cambio password se inserita
        if(!pwdEmpty)
        {
            currentUser?.updatePassword(newPwd)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Password aggiornata")
                }
            }
        }
       /*Toast.makeText(
                root.context, R.string.accountAlreadyExists,
                Toast.LENGTH_SHORT
        ).show()*/
        /*val modifier=HomeActivity()
        modifier.updateInformation("$name $surname",email)*/
        
    }

}