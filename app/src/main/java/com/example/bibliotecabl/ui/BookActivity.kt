package com.example.bibliotecabl.ui

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecabl.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    val database= Firebase.database.reference
    var activeRents=0
    var totalRents=0
    private lateinit var auth: FirebaseAuth
    var key=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        key = intent.getStringExtra(EXTRA_MESSAGE).toString()
        database.child("Books").child(key).get().addOnSuccessListener {
            bookTitleEditText.text=it.child("title").value.toString()
            bookAuthorEditText.text=it.child("author").value.toString()
            copiesEditText.text="copie: "+it.child("copies").value.toString()
            bookDescEditText.text=it.child("desc").value.toString()
            Picasso.get().load(it.child("bookImageUrl").value.toString()).into(imageIcon)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun onRentClick(view: View)
    {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        var copies=0
        database.child("Books").child(key).get().addOnSuccessListener {
            copies=it.child("copies").value.toString().toInt()
            if(copies>0)
            {
                database.child("Rented_Books").child(uid).get().addOnSuccessListener {
                    //Non si possono prenotare due libri uguali
                    if (!it.child("BookList").child(key).exists())
                    {
                        activeRents = 1 + it.child("active_rents").value.toString().toInt()
                        totalRents = 1 + it.child("total_rents").value.toString().toInt()
                        val bookMap = HashMap<String, Any>()
                        bookMap[key] = ""
                        database.child("Rented_Books").child(uid).child("BookList").updateChildren(bookMap).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val updateMap = HashMap<String, Any>()
                                updateMap["total_rents"] = totalRents
                                //database.child("Rented_Books").child(uid).updateChildren(updateMap)
                                updateMap["active_rents"] = activeRents
                                database.child("Rented_Books").child(uid).updateChildren(updateMap)
                                val copiesMap = HashMap<String, Any>()
                                copiesMap["copies"] = copies - 1
                                database.child("Books").child(key).updateChildren(copiesMap)
                                copiesEditText.text = "copie: "+ (copies-1)
                                Toast.makeText(
                                        baseContext, R.string.bookRented,
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(
                                baseContext, R.string.bookAlreadyRented,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    fun onReturnClick(view: View)
    {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        var copies=0
        database.child("Books").child(key).get().addOnSuccessListener {
            copies = it.child("copies").value.toString().toInt()
            database.child("Rented_Books").child(uid).get().addOnSuccessListener {
                if (it.child("BookList").child(key).exists())
                {
                    activeRents = 1 - it.child("active_rents").value.toString().toInt()
                    database.child("Rented_Books").child(uid).child("BookList").child(key).removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val updateMap = HashMap<String, Any>()
                            updateMap["active_rents"] = activeRents
                            database.child("Rented_Books").child(uid).updateChildren(updateMap)
                            val copiesMap = HashMap<String, Any>()
                            copiesMap["copies"] = copies + 1
                            database.child("Books").child(key).updateChildren(copiesMap)
                            copiesEditText.text = "copie: "+ (copies+1)
                            Toast.makeText(
                                    baseContext, R.string.bookReturned,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(
                            baseContext, R.string.bookNotOwned,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}