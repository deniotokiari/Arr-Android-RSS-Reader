package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.worker.ArticlesFetchAndCacheWorker
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(private val db: AppDatabase) : ViewModel() {

    private val hasFeedsLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val feedsLiveData: LiveData<List<RssFeed>> = db.rssFeedDao().all(1)
    private val observer: Observer<List<RssFeed>> = Observer {
        val isEmpty: Boolean = it.isEmpty()

        if (!isEmpty && hasFeedsLiveData.value == false) {
            hasFeedsLiveData.value = true
        }
    }

    fun hasRssFeeds(): LiveData<Boolean> {
        hasFeedsLiveData.value = false

        feedsLiveData.observeForever(observer)

        return hasFeedsLiveData
    }

    fun loadArticles() {
        GlobalScope.launch(bg) {
            val requests: List<OneTimeWorkRequest> = db
                .rssFeedDao()
                .allFeedsId()
                .map { item -> ArticlesFetchAndCacheWorker.getOneTimeRequest(item.id) }

            if (requests.isNotEmpty()) {
                WorkManager.getInstance().enqueue(requests)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        feedsLiveData.removeObserver(observer)
    }

}