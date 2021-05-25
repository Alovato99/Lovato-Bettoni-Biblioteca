package com.example.bibliotecabl.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SettingsViewModel : ViewModel() {

    private val database = Firebase.database.reference
    private var auth: FirebaseAuth = Firebase.auth
    private val currentUser = auth.currentUser
    private val uid = currentUser!!.uid

    private val _textName = MutableLiveData<String>().apply {

        database.child("Users").child(uid).child("name").get().addOnSuccessListener {
            value = it.getValue().toString()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

    }
    private val _textSurname = MutableLiveData<String>().apply {
        database.child("Users").child(uid).child("surname").get().addOnSuccessListener {
            value = it.getValue().toString()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
    private val _textEmail = MutableLiveData<String>().apply {
        value=auth.currentUser?.email.toString()
    }


    val textName: LiveData<String> = _textName
    val textSurname: LiveData<String> = _textSurname
    val textEmail: LiveData<String> = _textEmail


}