package by.deniotokiari.arr.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
        foreignKeys = [ForeignKey(
                entity = RssFeed::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("feed_id"),
                onDelete = ForeignKey.CASCADE
        )],
        indices = [Index(value = ["feed_id"])],
        primaryKeys = ["title", "description", "date"]
)
data class Article(
        var title: String,
        var description: String, // could be provided as text with html tags
        var category: String,
        @ColumnInfo(name = "feed_id")
        var feedId: Long?,
        var link: String?,
        var date: Long,
        var creator: String?,
        var read: Boolean = false
)