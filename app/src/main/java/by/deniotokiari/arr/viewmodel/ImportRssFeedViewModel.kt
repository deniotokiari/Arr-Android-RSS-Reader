package by.deniotokiari.arr.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.coroutines.launchBg
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import kotlinx.coroutines.Job
import java.io.InputStream

class ImportRssFeedViewModel(private val context: Context, private val db: AppDatabase) : ViewModel() {

    private var feeds: MutableLiveData<List<RssFeed>> = MutableLiveData()
    private var job: Job? = null

    override fun onCleared() {
        job?.cancel()
    }

    fun setFileUri(uri: Uri?) {
        job?.cancel()
        job = launchBg {
            val result: List<RssFeed>? = parseXml(getInputStream(context, uri))

            feeds.postValue(result)
        }
    }

    fun getFeedsCount(): LiveData<Int> = Transformations.map(feeds) { it.size }

    private fun parseXml(stream: InputStream?): List<RssFeed>? {
        return null
    }

    private fun getInputStream(context: Context, uri: Uri?): InputStream? {
        uri?.let {
            return context.contentResolver.openInputStream(uri)
        }

        return null
    }

}