package by.deniotokiari.arr.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.deniotokiari.arr.db.dao.ArticleDao
import by.deniotokiari.arr.db.dao.RssFeedDao
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.db.entity.RssFeed

@Database(
    entities = [
        RssFeed::class,
        Article::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rssFeedDao(): RssFeedDao

    abstract fun articleDao(): ArticleDao

}