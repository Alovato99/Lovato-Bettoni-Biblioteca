package com.example.bibliotecabl.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecabl.Book
import com.example.bibliotecabl.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.security.auth.callback.Callback



class BrowseBooksFragment: Fragment() {

    private val database = Firebase.database.reference.child("Books")
    private var booksList : ArrayList<Book> = ArrayList()




    fun testClickLinea(v: View)
    {
        Log.d("BookList", "cliclk click")
    }
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

        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists())
                {
                    for(b in snapshot.children)
                    {
                        val book = b.getValue(Book::class.java)
                        booksList.add(book!!)
                    }

                    rclView.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
                    rclView.adapter = VerticalItemAdapter(booksList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




        for(b in booksList)
            Log.d("BookList", b.title)



        return root
    }
}