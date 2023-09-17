package com.example.todolistapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DBDataViewModel : ViewModel() {
    val userID: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}