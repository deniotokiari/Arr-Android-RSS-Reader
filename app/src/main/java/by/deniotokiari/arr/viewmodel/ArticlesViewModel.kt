package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.Article

class ArticlesViewModel(private val db: AppDatabase) : ViewModel() {

    fun getLastArticles(): LiveData<List<Article>> = db.articleDao().getLastArticles(false)

    fun getLastArticlesById(feedId: Long): LiveData<List<Article>> = db.articleDao().getLastArticlesByFeedId(feedId, false)

}