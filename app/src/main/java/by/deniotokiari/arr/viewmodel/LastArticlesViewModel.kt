package by.deniotokiari.arr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.Article

class LastArticlesViewModel(private val db: AppDatabase) : ViewModel() {

    fun getArticles(): LiveData<List<Article>> = db.articleDao().getLastArticles(false)

}