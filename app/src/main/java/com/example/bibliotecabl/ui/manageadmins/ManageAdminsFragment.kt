package com.example.bibliotecabl.ui.manageadmins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotecabl.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_manage_admins.*

class ManageAdminsFragment: Fragment() {

    //private lateinit var manageAdminsViewModel: ManageAdminsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val database = Firebase.database.reference
        var auth: FirebaseAuth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        /*manageAdminsViewModel =
                ViewModelProvider(this).get(ManageAdminsViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_manage_admins, container, false)
        val addAdminButton: Button=root.findViewById(R.id.confirmAddAdminButton)
        val addAdminText: TextView=root.findViewById(R.id.addAdminEditText)
        val removeAdminButton: Button=root.findViewById(R.id.confirmRemoveAdminButton)
        val removeAdminText: TextView=root.findViewById(R.id.removeAdminEditText)
        /*val textView: TextView = root.findViewById(R.id.text_browse_books)
        manageAdminsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}