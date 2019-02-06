package by.deniotokiari.arr.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewholder.LastArticleViewHolder
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.stripHtml

class LastArticlesAdapter() : RecyclerView.Adapter<LastArticleViewHolder>() {

    val items: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastArticleViewHolder = LastArticleViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LastArticleViewHolder, position: Int) {
        val item: Article = items[position]

        holder.title.text = item.title
        holder.creatorAndPublishDate.text = "by ${item.creator} / ${item.date}"
        holder.description.text = item.description.stripHtml()

        holder.logo.gone()
    }



}

