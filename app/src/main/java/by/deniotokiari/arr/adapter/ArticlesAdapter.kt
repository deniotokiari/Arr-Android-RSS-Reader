package by.deniotokiari.arr.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewholder.ArticleViewHolder
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.stripHtml
import java.text.SimpleDateFormat

class ArticlesAdapter(private val format: SimpleDateFormat, private val template: String) : RecyclerView.Adapter<ArticleViewHolder>() {

    val items: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder = ArticleViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item: Article = items[position]

        holder.title.text = item.title
        holder.creatorAndPublishDate.text = String.format(template, item.creator, format.format(item.date))
        holder.description.text = item.description.stripHtml()

        holder.logo.gone()
    }

}

