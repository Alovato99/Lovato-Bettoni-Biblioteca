package com.example.bibliotecabl.ui.addbooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotecabl.R

class AddBooksFragment: Fragment() {

    private lateinit var addBooksViewModel: AddBooksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addBooksViewModel =
                ViewModelProvider(this).get(AddBooksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_books, container, false)
        val textView: TextView = root.findViewById(R.id.text_add_books)
        addBooksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}