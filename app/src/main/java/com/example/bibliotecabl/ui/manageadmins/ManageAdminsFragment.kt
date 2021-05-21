package com.example.bibliotecabl.ui.manageadmins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotecabl.R

class ManageAdminsFragment: Fragment() {

    private lateinit var manageAdminsViewModel: ManageAdminsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        manageAdminsViewModel =
                ViewModelProvider(this).get(ManageAdminsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_browse_books, container, false)
        val textView: TextView = root.findViewById(R.id.text_browse_books)
        manageAdminsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}