package by.deniotokiari.arr.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RssFeed(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var group: String,
    var source: String
)