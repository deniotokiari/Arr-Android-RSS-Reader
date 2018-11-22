package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MenuViewModel(private val db: AppDatabase) : ViewModel() {

    private val menuItems: MutableLiveData<List<MenuItem>> = MutableLiveData()

    fun getMenuItems(): LiveData<List<MenuItem>> = Transformations.switchMap(db.rssFeedDao().orderByGroup()) {
        GlobalScope.launch(bg) {

        }

        menuItems
    }

}

data class MenuItem(
    val title: String,
    val image: String? = null,
    val count: Int = 0
)