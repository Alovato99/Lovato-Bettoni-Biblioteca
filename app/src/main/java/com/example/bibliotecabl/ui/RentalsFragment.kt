package com.example.bibliotecabl.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

class RentalsFragment : Fragment() {


    private val bookDatabase = Firebase.database.reference.child("Books")
    private var booksList : MutableList<Book> = mutableListOf<Book>()
    private var displayList : MutableList<Book> = mutableListOf<Book>()
    lateinit var rclView : RecyclerView
    private var auth: FirebaseAuth = Firebase.auth
    private val currentUser = auth.currentUser
    private val uid = currentUser!!.uid
    private val database = Firebase.database.reference.child("Rented_Books").child(uid).child("BookList")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_rentals, container, false)
        //val textView: TextView = root.findViewById(R.id.text_rentals)
        rclView=root.findViewById(R.id.rentals_recycler_view)

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //if(snapshot.key!="BookList")
                //{
                displayList.clear()
                booksList.clear()
                if(snapshot.exists()) {
                    for (b in snapshot.children) {
                        bookDatabase.child(b.key.toString()).get().addOnSuccessListener {
                            val book = it.getValue(Book::class.java)
                            booksList.add(book!!)
                            displayList.add(book!!)
                            rclView.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
                            rclView.adapter = VerticalItemAdapter(displayList)
                        }
                        /*val book = b.getValue(Book::class.java)
                        booksList.add(book!!)*/
                    }
                }
                else
                {
                    rclView.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
                    rclView.adapter = VerticalItemAdapter(displayList)
                }

                    //displayList.clear()
                    //displayList.addAll(booksList)
                    //rclView.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
                    //rclView.adapter = VerticalItemAdapter(displayList)
                    //rclView.adapter = VerticalItemAdapter(booksList)

               //}
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        /*database.get().addOnSuccessListener {
            for(b in it.children) {
                bookDatabase.child(b.key.toString()).get().addOnSuccessListener {
                    val book = it.getValue(Book::class.java)
                    booksList.add(book!!)
                    displayList.add(book!!)
                    rclView.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
                    rclView.adapter = VerticalItemAdapter(displayList)
                }
            }
        }*/


        for(b in booksList)
            Log.d("BookList", b.title)


        return root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_main_search,menu)
        val searchItem=menu.findItem(R.id.menu_search)
        if(searchItem!=null)
        {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty())
                    {
                        displayList.clear()

                        val search=newText.toLowerCase()
                        booksList.forEach{
                            val author=it.author.toLowerCase()
                            val title=it.title.toLowerCase()
                            if(author.contains(search)||title.contains(search))
                            {
                                displayList.add(it)
                            }
                        }

                        rclView.adapter?.notifyDataSetChanged()
                    }
                    else
                    {
                        displayList.clear()
                        displayList.addAll(booksList)
                        rclView.adapter?.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
    }
}