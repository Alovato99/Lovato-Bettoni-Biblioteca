package com.example.bibliotecabl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bibliotecabl.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ManageAdminsFragment: Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val database = Firebase.database.reference
        val root = inflater.inflate(R.layout.fragment_manage_admins, container, false)
        val addAdminButton: Button=root.findViewById(R.id.confirmAddAdminButton)
        val addAdminText: TextView=root.findViewById(R.id.addAdminEditText)
        val removeAdminButton: Button=root.findViewById(R.id.confirmRemoveAdminButton)
        val removeAdminText: TextView=root.findViewById(R.id.removeAdminEditText)

//        database.child("Users" ).
//
        addAdminButton.setOnClickListener()
        {
            database.child("emailToUID").child(addAdminText.text.toString().replace(".",",")).get().addOnSuccessListener {
                Log.d("READING_EMAIL_TO_UID", it.getValue() as String)
                val userMap = HashMap<String, Any>()
                userMap["admin"] = true
                database.child("Users").child(it.getValue() as String).updateChildren(userMap)


            }.addOnFailureListener{
                Log.d("READING_EMAIL_TO_UID", "FAIL")
            }
        }

        removeAdminButton.setOnClickListener()
        {
            database.child("emailToUID").child(removeAdminText.text.toString().replace(".",",")).get().addOnSuccessListener {
                Log.d("READING_EMAIL_TO_UID", it.getValue() as String)
                val userMap = HashMap<String, Any>()
                userMap["admin"] = false
                database.child("Users").child(it.getValue() as String).updateChildren(userMap)


            }.addOnFailureListener{
                Log.d("READING_EMAIL_TO_UID", "FAIL")
            }
        }



        return root
    }
}



