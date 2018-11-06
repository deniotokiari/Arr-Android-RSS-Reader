package by.deniotokiari.arr.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import by.deniotokiari.arr.db.entity.RssFeed

@Dao
interface RssFeedDao {

    @Insert
    fun insert(vararg feeds: RssFeed)

    @Update
    fun update(vararg feeds: RssFeed)

    @Delete
    fun delete(vararg feeds: RssFeed)

    @Query("SELECT * FROM rssfeed")
    fun all(): LiveData<List<RssFeed>>

    @Query("SELECT * FROM rssfeed LIMIT :limit")
    fun all(limit: Int): LiveData<List<RssFeed>>

    @Query("SELECT * FROM rssfeed WHERE id = :id")
    fun byId(id: Int): LiveData<RssFeed>

    @Query("SELECT * FROM rssfeed WHERE `group` = :group")
    fun byGroup(group: String): LiveData<List<RssFeed>>

}