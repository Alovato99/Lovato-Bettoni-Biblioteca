package com.example.bibliotecabl.ui.manageadmins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManageAdminsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Prova manage books"
    }
    val text: LiveData<String> = _text
}