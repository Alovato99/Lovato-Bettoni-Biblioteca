package com.example.bibliotecabl

import android.text.format.DateFormat

class Book(val copies: Int, val author: String, val bookImageUrl: String, val currentDate: String, val title: String, val desc : String){
    constructor() : this(0, "","","","",""){}
}