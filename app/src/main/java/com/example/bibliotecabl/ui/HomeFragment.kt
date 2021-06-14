package com.example.bibliotecabl.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class HomeFragment : Fragment() {

    lateinit var rclViewNewBooks : RecyclerView
    lateinit var rclViewRentBooks : RecyclerView
    private val database = Firebase.database.reference.child("Books")
    private var booksList : ArrayList<Book> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home_new)


        rclViewNewBooks=root.findViewById(R.id.books_recycler_view_home_new)
        rclViewRentBooks=root.findViewById(R.id.books_recycler_view_home_rents)


        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists())
                {
                    for(b in snapshot.children)
                    {
                        val book = b.getValue(Book::class.java)
                        booksList.add(book!!)
                    }

                    rclViewNewBooks.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.HORIZONTAL, false)
                    rclViewNewBooks.adapter = HorizontalItemAdapter(booksList)
                    rclViewRentBooks.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.HORIZONTAL, false)
                    rclViewRentBooks.adapter = HorizontalItemAdapter(booksList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        textView.setOnClickListener()
        {
            val intent = Intent(context, BookActivity::class.java)
            startActivity(intent)
        }
        return root
    }
}