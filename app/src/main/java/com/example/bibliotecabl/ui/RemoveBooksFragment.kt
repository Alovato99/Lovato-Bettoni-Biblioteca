package com.example.bibliotecabl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bibliotecabl.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_remove_books.*
import java.lang.NullPointerException

class RemoveBooksFragment : Fragment() {

    private var title=""
    private var author=""
    private var bookID : String =""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_remove_books, container, false)
        val removeBook: Button = root.findViewById(R.id.confirmRemoveBookButton)
        removeBook.setOnClickListener()
        {
            title=removeBookTitleEditText.text.toString()
            author=removeBookAuthorEditText.text.toString()
            bookID= title.replace(" ", "-") + "-" + author.replace(" ", "-") //EXAMPLE: "Rose-Viola-Nicola-Pascoli"
            if(title.isNotEmpty() && author.isNotEmpty())
            {
                val database = Firebase.database.reference


                    database.child("Books").child(bookID).get().addOnSuccessListener {
                        var bookLink=it.child("imageID").getValue().toString()
                        FirebaseStorage.getInstance().getReference().child("images").child(bookLink).delete()
                    }


                database.child("Books").child(bookID).removeValue()
                Toast.makeText(
                        activity, R.string.bookRemoved,
                        Toast.LENGTH_SHORT
                ).show()
                database.child("New_Arrivals").get().addOnSuccessListener {
                    var found=-1
                    for(i in 0..9)
                    {
                        if(it.child("BookList").child(i.toString()).getValue().toString() == bookID)
                            found=i
                    }
                    if(found>=0)
                    {
                        val bookMap = HashMap<String, Any>()
                        val countMap = HashMap<String, Any>()
                        var lastElement=0
                        if(found<9)
                        {
                            var nextBook = ""
                            for (i in found..8) {
                                nextBook = it.child("BookList").child((i+1).toString()).getValue().toString()
                                if(nextBook!="")
                                {
                                    bookMap[i.toString()]=nextBook
                                    //database.child("New_Arrivals").child("BookList").updateChildren(bookMap)
                                    if(i==8)
                                        lastElement=i+1
                                }
                                else
                                {
                                    lastElement=i
                                    break
                                }
                            }
                            bookMap[(lastElement).toString()]=""
                            database.child("New_Arrivals").child("BookList").updateChildren(bookMap)
                            countMap["count"]=lastElement
                            database.child("New_Arrivals").updateChildren(countMap)
                        }
                        else if(found==9)
                        {
                            bookMap["9"]=""
                            database.child("New_Arrivals").child("BookList").updateChildren(bookMap)
                            countMap["count"]=9
                            database.child("New_Arrivals").updateChildren(countMap)
                        }
                    }
                }
            }
        }
        return root
    }

}