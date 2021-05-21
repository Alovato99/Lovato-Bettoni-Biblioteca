package com.example.bibliotecabl.ui.addbooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddBooksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Prova add books"
    }
    val text: LiveData<String> = _text
}