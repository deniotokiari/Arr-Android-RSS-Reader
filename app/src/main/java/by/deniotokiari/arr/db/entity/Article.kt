package by.deniotokiari.arr.db.entity

import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = RssFeed::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("feed_id"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["feed_id"])],
    primaryKeys = ["title", "description"]
)
data class Article(
    var title: String,
    var description: String,
    var category: String,
    @ColumnInfo(name = "feed_id")
    var feedId: Long?,
    var link: String?,
    var date: Long?,
    var creator: String?,
    var read: Boolean = false
)