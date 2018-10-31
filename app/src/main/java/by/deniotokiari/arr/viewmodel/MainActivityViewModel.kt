package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed

class MainActivityViewModel(private val db: AppDatabase) : ViewModel() {


    override fun onCleared() {

    }

    fun hasRssFeeds(): LiveData<Boolean> =
        Transformations.map(db.rssFeedDao().all(1)) { feeds: List<RssFeed>? -> feeds?.isEmpty()?.not() }

}