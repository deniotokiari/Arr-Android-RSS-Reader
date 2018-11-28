package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MenuViewModel(private val db: AppDatabase, private val uncategorized: String) : ViewModel() {

    private val menuItems: MutableLiveData<List<MenuItem>> = MutableLiveData()

    fun getMenuItems(): LiveData<List<MenuItem>> = Transformations.switchMap(db.rssFeedDao().groups()) {
        GlobalScope.launch(bg) {
            menuItems.postValue(
                it
                    .map { item ->
                        val items: List<RssFeed>? = async(bg) { db.rssFeedDao().feedsByGroup(item.title) }.await()
                        val count: Int = async(bg) { db.articleDao().countByGroup(item.title, false) }.await()
                        val feedItems: List<Feed>? = items
                            ?.map { feed -> Feed(feed, db.articleDao().countByGroupId(feed.id)) }

                        val title: String = if (item.title.isEmpty()) uncategorized else item.title

                        MenuItem(title = title, items = feedItems, count = count)
                    }
            )
        }

        menuItems
    }

}

data class MenuItem(
    val title: String,
    val count: Int = 0,
    val image: String? = null,
    val items: List<Feed>? = null
)

data class Feed(
    val feed: RssFeed,
    val count: Int
)