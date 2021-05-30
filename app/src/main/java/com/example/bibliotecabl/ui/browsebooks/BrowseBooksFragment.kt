package com.example.bibliotecabl.ui.browsebooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.R
import com.example.bibliotecabl.ui.ItemAdapter
import kotlinx.android.synthetic.main.fragment_browse_books.*

class BrowseBooksFragment: Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_browse_books, container, false)
        val rclView : RecyclerView = root.findViewById(R.id.books_recycler_view)

        val items: ArrayList<String> = ArrayList()
        for(i in 1..100){
            items.add("item n $i")
        }


        rclView.layoutManager = LinearLayoutManager(activity?.baseContext, OrientationHelper.HORIZONTAL, false)
        rclView.adapter = ItemAdapter(items)

        return root
    }
}