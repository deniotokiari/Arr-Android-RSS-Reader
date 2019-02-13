package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleReadabilityDetailsViewModel : ViewModel() {

    private val readability: MutableLiveData<Boolean> = MutableLiveData()

    fun getReadability(): LiveData<Boolean> = readability

    fun updateReadability(value: Boolean) {
        readability.value = value
    }

}