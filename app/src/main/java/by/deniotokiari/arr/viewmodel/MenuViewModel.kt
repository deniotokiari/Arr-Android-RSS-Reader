package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MenuViewModel(private val db: AppDatabase) : ViewModel() {

    private val menuItems: MutableLiveData<List<MenuItem>> = MutableLiveData()

    fun getMenuItems(): LiveData<List<MenuItem>> = Transformations.switchMap(db.rssFeedDao().groups()) {
        GlobalScope.launch(bg) {
            menuItems.postValue(it.map { item ->
                MenuItem(title = item.title, items = db.rssFeedDao().feedsByGroup(item.title))
            })
        }

        menuItems
    }

}

data class MenuItem(
    val title: String,
    val count: Int = 0,
    val image: String? = null,
    val items: List<RssFeed>? = null
)