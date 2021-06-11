package com.example.bibliotecabl.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bibliotecabl.HomeActivity
import com.example.bibliotecabl.LoginActivity
import com.example.bibliotecabl.R

class HomeFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        textView.setOnClickListener()
        {
            val intent = Intent(context, BookActivity::class.java)
            startActivity(intent)
        }
        return root
    }
}