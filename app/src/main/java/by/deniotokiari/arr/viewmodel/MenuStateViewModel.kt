package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuStateViewModel : ViewModel() {

    private val isMenuOpen: MutableLiveData<Boolean> = MutableLiveData()
    private val shouldOpenMenu: MutableLiveData<Boolean> = MutableLiveData()

    fun isMenuOpen(state: Boolean) {
        isMenuOpen.value = state
    }

    fun isMenuOpen(): LiveData<Boolean> = isMenuOpen

    fun shouldOpenMenu(state: Boolean) {
        shouldOpenMenu.value = state
    }

    fun shouldOpenMenu(): LiveData<Boolean> = shouldOpenMenu

}