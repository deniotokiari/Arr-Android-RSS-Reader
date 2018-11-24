package by.deniotokiari.arr.db.dao

import androidx.room.Dao
import androidx.room.Insert
import by.deniotokiari.arr.db.entity.Article

@Dao
interface ArticleDao {

    @Insert
    fun insert(article: Article): Long

}