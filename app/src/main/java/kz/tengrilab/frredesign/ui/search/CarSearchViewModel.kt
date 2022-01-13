package kz.tengrilab.frredesign.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarSearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is car search Fragment"
    }
    val text: LiveData<String> = _text
}