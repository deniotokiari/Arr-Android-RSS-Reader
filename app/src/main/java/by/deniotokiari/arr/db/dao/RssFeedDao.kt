package by.deniotokiari.arr.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import by.deniotokiari.arr.db.entity.RssFeed

@Dao
interface RssFeedDao {

    @Insert
    fun insert(feed: RssFeed): Long

    @Insert
    fun insert(feeds: List<RssFeed>): LongArray

    @Update
    fun update(vararg feeds: RssFeed)

    @Delete
    fun delete(vararg feeds: RssFeed)

    @Query("SELECT * FROM rssfeed")
    fun all(): LiveData<List<RssFeed>>

    @Query("SELECT * FROM rssfeed LIMIT :limit")
    fun all(limit: Int): LiveData<List<RssFeed>>

    @Query("SELECT * FROM rssfeed WHERE `group` = :group")
    fun feedsByGroup(group: String): List<RssFeed>

    @Query("SELECT `group` AS title FROM rssfeed GROUP BY `group` ORDER BY `group`")
    fun groups(): LiveData<List<RssGroup>>

    @Query("SELECT * FROM rssfeed WHERE id = :id")
    fun feedById(id: Long): RssFeed?

}

data class RssGroup(val title: String)