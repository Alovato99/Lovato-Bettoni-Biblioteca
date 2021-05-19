package com.example.bibliotecabl.ui.rentals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotecabl.R

class RentalsFragment : Fragment() {

    private lateinit var rentalsViewModel: RentalsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rentalsViewModel =
                ViewModelProvider(this).get(RentalsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_rentals, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        rentalsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}