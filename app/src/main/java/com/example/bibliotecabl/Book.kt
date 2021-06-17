package com.example.bibliotecabl

import android.text.format.DateFormat

class Book(val copies: Int, var author: String, val bookImageUrl: String, val currentDate: String, var title: String, var desc : String){
    constructor() : this(0, "","","","",""){}
}