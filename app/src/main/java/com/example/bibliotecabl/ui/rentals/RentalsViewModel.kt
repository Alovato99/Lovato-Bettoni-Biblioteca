package com.example.bibliotecabl.ui.rentals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RentalsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Prova Prenotazioni"
    }
    val text: LiveData<String> = _text
}