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
    primaryKeys = ["title", "date", "description"]
)
data class Article(
    var title: String,
    var date: Long,
    var description: String, // could be provided as text with html tags
    @ColumnInfo(name = "feed_id")
    var feedId: Long? = null,
    var link: String? = null,
    var creator: String? = null,

    // local
    var read: Boolean = false,
    var logo: String? = getLogo(description),
    var shortDescription: String = getShortDescription(description)
)

private fun getLogo(description: String): String? {

}

private fun getShortDescription(description: String): String {

}