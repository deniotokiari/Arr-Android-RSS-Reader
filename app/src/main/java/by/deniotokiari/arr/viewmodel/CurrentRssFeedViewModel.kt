package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.entity.RssFeed

class CurrentRssFeedViewModel : ViewModel() {

    private val feed: MutableLiveData<RssFeed> = MutableLiveData()

    fun setCurrentItem(feed: RssFeed) {
        this.feed.value = feed
    }

    fun getCurrentItem(): LiveData<RssFeed> = feed

}