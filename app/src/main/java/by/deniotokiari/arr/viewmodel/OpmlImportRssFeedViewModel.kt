package by.deniotokiari.arr.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.InputStream

class OpmlImportRssFeedViewModel(private val context: Context, private val db: AppDatabase) : ViewModel() {

    private var feeds: MutableLiveData<List<RssFeed>> = MutableLiveData()
    private var job: Job? = null

    override fun onCleared() {
        job?.cancel()
    }

    internal fun parseOpml(stream: InputStream?): List<RssFeed>? {
        return null
    }

    fun setFileUri(uri: Uri?) {
        fun getInputStream(context: Context, uri: Uri?): InputStream? = uri?.let { context.contentResolver.openInputStream(uri) }

        job?.cancel()

        job = GlobalScope.launch(bg) {
            val result: List<RssFeed>? = parseOpml(getInputStream(context, uri))

            result?.also {
                db.rssFeedDao().insert(it)
            }

            feeds.postValue(result)
        }
    }

    fun getFeedsCount(): LiveData<Int> = Transformations.map(feeds) { it?.size }

}