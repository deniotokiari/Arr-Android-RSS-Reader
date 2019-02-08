package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getArticles(feedId: Long? = null): LiveData<List<Article>> {
        GlobalScope.launch(bg) {
            val result: List<Article> = if (feedId == null) db.articleDao().getLastArticlesSync(false) else db.articleDao().getLastArticlesByFeedIdSync(feedId, false)

            articles.postValue(result)
        }

        return articles
    }

}