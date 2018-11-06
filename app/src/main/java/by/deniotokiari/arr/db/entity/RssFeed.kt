package by.deniotokiari.arr.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RssFeed(
    var title: String,
    var group: String,
    var source: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)