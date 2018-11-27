package by.deniotokiari.arr.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.deniotokiari.arr.db.entity.Article

@Dao
interface ArticleDao {

    @Insert
    fun insert(article: Article): Long

    @Query("SELECT * FROM article")
    fun all(): LiveData<List<Article>>

}