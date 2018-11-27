package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.worker.ArticlesFetchAndCacheWorker
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(private val db: AppDatabase) : ViewModel() {

    fun hasRssFeeds(): LiveData<Boolean> = Transformations.map(db.rssFeedDao().all(1)) { feeds: List<RssFeed>? -> feeds?.isEmpty()?.not() }

    fun loadArticles() {
        GlobalScope.launch(bg) {
            val requests: List<OneTimeWorkRequest> = db
                .rssFeedDao()
                .allFeedsId()
                .map { item ->
                    val data: Data = Data.Builder()
                        .putLong(ArticlesFetchAndCacheWorker.FEED_ID, item.id)
                        .build()
                    OneTimeWorkRequestBuilder<ArticlesFetchAndCacheWorker>()
                        .setInputData(data)
                        .build()
                }

            WorkManager.getInstance().enqueue(requests)
        }
    }

}