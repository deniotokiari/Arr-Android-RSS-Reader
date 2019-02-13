package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticlesViewModel(private val db: AppDatabase) : ViewModel() {

    private val articles: MutableLiveData<List<Article>> = MutableLiveData()

    fun getLastArticles(feedId: Long? = null): LiveData<List<Article>> = if (feedId == null) {
        db.articleDao().getLastArticles(false)
    } else {
        db.articleDao().getLastArticlesByFeedId(feedId, false)
    }

    // TODO: need to modify to binary search
    fun getArticleIndex(title: String, date: Long): LiveData<Int> = Transformations.map(articles) {
        it.find { article -> article.title == title && article.date == date }?.let { article -> it.indexOf(article) }
    }

}

data class