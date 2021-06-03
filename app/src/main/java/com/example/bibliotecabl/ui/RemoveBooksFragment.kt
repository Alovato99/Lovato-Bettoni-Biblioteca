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


                    /*database.child("Books").child(bookID).get().addOnSuccessListener {
                        var bookLink=it.child("bookImageUrl").getValue().toString()
                        FirebaseStorage.getInstance().getReference().child("images").child(bookLink).delete()
                    }*/


                database.child("Books").child(bookID).removeValue()
                Toast.makeText(
                        activity, R.string.bookRemoved,
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
        return root
    }

}