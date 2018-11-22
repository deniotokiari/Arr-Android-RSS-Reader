package by.deniotokiari.arr.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.livedata.RssFeedFromOpmlLiveData
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OpmlImportRssFeedViewModel(private val context: Context, private val db: AppDatabase) : ViewModel() {

    private val uri: MutableLiveData<Uri> = MutableLiveData()
    private val feedsToImport: MutableLiveData<ArrayList<RssFeed>> = MutableLiveData()
    private val feedsFromFile: LiveData<List<RssFeed>> = Transformations.switchMap(uri) { RssFeedFromOpmlLiveData(context, it) }

    fun setUri(uri: Uri) {
        this.uri.value = uri
    }

    fun addFeedToImport(feed: RssFeed) {
        var feeds: ArrayList<RssFeed>? = feedsToImport.value

        if (feeds == null) {
            feeds = ArrayList()
        }

        feeds.add(feed)

        feedsToImport.value = feeds
    }

    fun removeFeedToImport(feed: RssFeed) {
        val feeds: ArrayList<RssFeed>? = feedsToImport.value

        feeds?.remove(feed)

        feedsToImport.value = feeds
    }

    fun containsFeedToImport(feed: RssFeed): Boolean = feedsToImport.value?.contains(feed) ?: false

    fun getRssFeeds(): LiveData<List<RssFeed>> = feedsFromFile

    fun getFeedsToImport(): LiveData<ArrayList<RssFeed>> = feedsToImport

    fun addOrRemoveAllFeeds() {
        fun addAllFeeds() {
            var feeds: ArrayList<RssFeed>? = feedsToImport.value

            if (feeds == null) {
                feeds = ArrayList()
            }

            feedsFromFile.value?.also {
                feeds.addAll(it)

                feedsToImport.value = feeds
            }
        }

        fun removeAllFeeds() {
            feedsToImport.value = ArrayList()
        }

        if (feedsToImport.value?.isEmpty() == false) {
            removeAllFeeds()
        } else {
            addAllFeeds()
        }
    }

    fun importSelectedFeeds() {
        feedsToImport.value?.also {
            if (!it.isEmpty()) {
                GlobalScope.launch(bg) {
                    db.rssFeedDao().insert(it)
                }
            }
        }
    }

}