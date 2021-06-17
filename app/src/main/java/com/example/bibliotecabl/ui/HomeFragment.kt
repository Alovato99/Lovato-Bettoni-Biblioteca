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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    lateinit var rclViewNewBooks : RecyclerView
    lateinit var rclViewRentBooks : RecyclerView
    private var auth: FirebaseAuth = Firebase.auth
    private val currentUser = auth.currentUser
    private val uid = currentUser!!.uid
    private val database_books_reference = Firebase.database.reference.child("Books")
    private val database_rents_reference = Firebase.database.reference.child("Rented_Books").child(uid)
    private var booksList : MutableList<Book> = mutableListOf<Book>()
    private var rentalsList : MutableList<Book> = mutableListOf<Book>()



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home_new)


        rclViewNewBooks=root.findViewById(R.id.books_recycler_view_home_new)
        rclViewRentBooks=root.findViewById(R.id.books_recycler_view_home_rents)


        database_books_reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                booksList.clear()
                if(snapshot!!.exists())
                {
                    for(b in snapshot.children)
                    {
                        val book = b.getValue(Book::class.java)
                        booksList.add(book!!)
                    }

                    rclViewNewBooks.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.HORIZONTAL, false)
                    rclViewNewBooks.adapter = HorizontalItemAdapter(booksList)
                    /*rclViewRentBooks.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.HORIZONTAL, false)
                    rclViewRentBooks.adapter = HorizontalItemAdapter(booksList)*/

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        database_rents_reference.child("BookList").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rentalsList.clear()
                if(snapshot.exists()) {
                    for (b in snapshot.children) {
                        database_books_reference.child(b.key.toString()).get().addOnSuccessListener {
                            val book = it.getValue(Book::class.java)
                            rentalsList.add(book!!)
                            rclViewRentBooks.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.HORIZONTAL, false)
                            rclViewRentBooks.adapter = HorizontalItemAdapter(rentalsList)
                        }
                        /*val book = b.getValue(Book::class.java)
                        booksList.add(book!!)*/
                    }
                }
                else
                {
                    rclViewRentBooks.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.HORIZONTAL, false)
                    rclViewRentBooks.adapter = HorizontalItemAdapter(rentalsList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        val activeRents : TextView = root.findViewById(R.id.text_home_active_rents)
        val totalRents : TextView = root.findViewById(R.id.text_home_total_rents)

        database_rents_reference.get().addOnSuccessListener {
            activeRents.text =getString(R.string.active_rents) +" "+ it.child("active_rents").value.toString()
            totalRents.text = getString(R.string.total_rents) +" "+ it.child("total_rents").value.toString()
        }

        return root
    }
}