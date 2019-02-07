package by.deniotokiari.arr.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R

class ArticleViewHolder(root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_article, root, false)) {

    val logo: ImageView = itemView.findViewById(R.id.logo)
    val title: TextView = itemView.findViewById(R.id.title)
    val creatorAndPublishDate: TextView = itemView.findViewById(R.id.creator_and_publish_date)
    val description: TextView = itemView.findViewById(R.id.description)

}