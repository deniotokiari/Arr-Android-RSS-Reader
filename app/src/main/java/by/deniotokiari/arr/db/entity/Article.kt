package by.deniotokiari.arr.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RssFeed::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("feed_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Article(
    var title: String,
    var description: String,
    var category: String,
    @ColumnInfo(name = "feed_id")
    var feedId: Long?,
    var link: String?,
    var date: String?,
    var creator: String?,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var read: Boolean = false
)