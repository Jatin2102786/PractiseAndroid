package com.jatin.practiseandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> get() = _counter

    fun increaseCount() {
        _counter.value = _counter.value?.plus(1)
    }
}