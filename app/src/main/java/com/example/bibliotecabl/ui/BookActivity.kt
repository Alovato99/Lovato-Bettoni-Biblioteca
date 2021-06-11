package com.example.bibliotecabl.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.bibliotecabl.R
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun onRentClick(view: View)
    {
        finish()
    }
}