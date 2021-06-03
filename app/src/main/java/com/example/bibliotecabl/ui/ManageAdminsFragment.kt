package com.example.bibliotecabl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bibliotecabl.R
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.NullPointerException

class ManageAdminsFragment: Fragment() {

    var addAdmin=""
    var removeAdmin=""

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

                addAdmin = addAdminText.text.toString()
                if (addAdmin.isNotEmpty()) {
                    database.child("emailToUID").child(addAdmin.replace(".", ",")).get().addOnSuccessListener {
                        val userMap = HashMap<String, Any>()
                        userMap["admin"] = true
                        try {
                            database.child("Users").child(it.getValue() as String).updateChildren(userMap)
                            Toast.makeText(
                                    activity, R.string.adminAdded,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                        catch (e: java.lang.NullPointerException)
                        {
                            Toast.makeText(
                                    activity, R.string.emailDoesntExists,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }

                    }.addOnFailureListener {
                        Log.d("READING_EMAIL_TO_UID", "FAIL")
                    }
                }
        }

        removeAdminButton.setOnClickListener()
        {
            removeAdmin=removeAdminText.text.toString()
            if(removeAdmin.isNotEmpty()) {
                database.child("emailToUID").child(removeAdmin.replace(".", ",")).get().addOnSuccessListener {
                    val userMap = HashMap<String, Any>()
                    userMap["admin"] = false
                    try {
                        database.child("Users").child(it.getValue() as String).updateChildren(userMap)
                        Toast.makeText(
                                activity, R.string.adminRemoved,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                    catch(e: java.lang.NullPointerException)
                    {
                        Toast.makeText(
                                activity, R.string.emailDoesntExists,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                }.addOnFailureListener {
                    Log.d("READING_EMAIL_TO_UID", "FAIL")
                }
            }
        }



        return root
    }
}



