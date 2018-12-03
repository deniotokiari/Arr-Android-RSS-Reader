package by.deniotokiari.arr.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.deniotokiari.arr.db.entity.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: List<Article>): LongArray

    @Query("SELECT * FROM article")
    fun all(): LiveData<List<Article>>

    @Query("SELECT COUNT(*) from article AS article, rssfeed AS feed WHERE feed.`group` = :group AND article.feed_id = feed.id AND article.read = :read")
    fun countByGroup(group: String, read: Boolean): Int

    @Query("SELECT * FROM article WHERE read = :read ORDER BY date")
    fun getLastArticles(read: Boolean): LiveData<List<Article>>

    @Query("SELECT COUNT(*) FROM article WHERE feed_id = :id")
    fun countByGroupId(id: Long?): Int

}