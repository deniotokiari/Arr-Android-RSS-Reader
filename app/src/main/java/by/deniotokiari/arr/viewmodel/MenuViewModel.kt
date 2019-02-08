package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.core.coroutines.bg
import by.deniotokiari.core.extensions.pmap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MenuViewModel(private val db: AppDatabase, private val uncategorized: String, private val lastArticles: String) : ViewModel() {

    private var previousMenuItems: List<MenuItem> by Delegates.observable(ArrayList()) { _, oldValue, newValue ->
        GlobalScope.launch(bg) {
            val diffUtilCallback = MenuItemsDiffUtilCallback(newValue, oldValue)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)


            menuItemsDiff.postValue(diffResult)
        }
    }

    private val menuItems: MutableLiveData<List<MenuItem>> = MutableLiveData()
    private val menuItemsDiff: MutableLiveData<DiffUtil.DiffResult> = MutableLiveData()

    fun getMenuItems(): LiveData<List<MenuItem>> = Transformations.switchMap(db.rssFeedDao().groups()) {
        GlobalScope.launch(bg) {
            val menuItemsResult: List<MenuItem> = it
                .pmap { item ->
                    val items: List<RssFeed>? = async(bg) { db.rssFeedDao().feedsByGroup(item.title) }.await()
                    val count: Int = async(bg) { db.articleDao().countByGroup(item.title, false) }.await()
                    val feedItems: List<Feed>? = items?.pmap { feed -> Feed(feed, db.articleDao().countByGroupId(feed.id)) }
                    val title: String = if (item.title.isEmpty()) uncategorized else item.title

                    MenuItem(title = title, items = feedItems, count = count)
                }

            val result: ArrayList<MenuItem> = ArrayList(menuItemsResult.size + 1)

            result.add(MenuItem(lastArticles, reserved = true))
            result.addAll(menuItemsResult)

            menuItems.postValue(result)

            previousMenuItems = result
        }

        menuItems
    }

    fun getMenuItemsDiff(): LiveData<DiffUtil.DiffResult> = menuItemsDiff

}

private class MenuItemsDiffUtilCallback(val newItems: List<MenuItem>, val oldItems: List<MenuItem>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: MenuItem = oldItems[oldItemPosition]
        val newItem: MenuItem = newItems[newItemPosition]

        return oldItem.title == newItem.title
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: MenuItem = oldItems[oldItemPosition]
        val newItem: MenuItem = newItems[newItemPosition]

        return oldItem.count == newItem.count
                && oldItem.count == newItem.count
    }
}

data class MenuItem(
    val title: String,
    val count: Int = 0,
    val items: List<Feed>? = null,
    val reserved: Boolean = false
)

data class Feed(
    val feed: RssFeed,
    val count: Int
)