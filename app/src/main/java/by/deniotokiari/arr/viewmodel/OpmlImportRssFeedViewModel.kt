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

class OpmlImportRssFeedViewModel(private val context: Context, private val db: AppDatabase) : ViewModel() {

    private val uri: MutableLiveData<Uri> = MutableLiveData()

    fun setUri(uri: Uri) {
        this.uri.value = uri
    }

    fun getRssFeeds(): LiveData<List<RssFeed>> = Transformations.switchMap(uri) { RssFeedFromOpmlLiveData(context, it) }

}