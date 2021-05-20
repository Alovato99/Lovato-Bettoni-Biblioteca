package com.example.bibliotecabl.ui.browsebooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BrowseBooksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Prova books"
    }
    val text: LiveData<String> = _text
}