package com.example.bibliotecabl.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
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
    private var displayList : ArrayList<Book> = ArrayList()
    lateinit var rclView : RecyclerView




    fun testClickLinea(v: View)
    {
        Log.d("BookList", "cliclk click")
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_browse_books, container, false)
        //val rclView : RecyclerView = root.findViewById(R.id.books_recycler_view)
        rclView=root.findViewById(R.id.books_recycler_view)

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

                   //displayList.clear()
                    displayList.addAll(booksList)
                    rclView.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)
                    rclView.adapter = VerticalItemAdapter(displayList)
                    //rclView.adapter = VerticalItemAdapter(booksList)

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
    override fun onCreateOptionsMenu(menu: Menu, inflater:MenuInflater) {
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