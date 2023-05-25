package com.zd.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZdViewModel : ViewModel() {
    val list: MutableLiveData<List<String>> =  MutableLiveData()
}