package com.example.bibliotecabl.ui.browsebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotecabl.R

class BrowseBooksFragment: Fragment() {

    private lateinit var browseBooksViewModel: BrowseBooksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        browseBooksViewModel =
                ViewModelProvider(this).get(BrowseBooksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragmen_browse_books, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        browseBooksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}