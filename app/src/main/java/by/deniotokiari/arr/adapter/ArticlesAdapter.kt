package by.deniotokiari.arr.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewholder.ArticleViewHolder
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.stripHtml
import by.deniotokiari.core.extensions.visible
import by.deniotokiari.core.imageloader.IImageLoader
import by.deniotokiari.core.recyclerview.OnItemClickListener
import java.text.SimpleDateFormat

class ArticlesAdapter(
    private val format: SimpleDateFormat,
    private val template: String,
    private val itemClickListener: OnItemClickListener<Article>,
    private val imageLoader: IImageLoader
) : RecyclerView.Adapter<ArticleViewHolder>() {

    private val clickListener: View.OnClickListener = View.OnClickListener {
        val item: Article = it.tag as Article

        itemClickListener.onItemClick(-1, item)
    }

    val items: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder = ArticleViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item: Article = items[position]

        holder.title.text = item.title
        holder.creatorAndPublishDate.text = String.format(template, item.creator, format.format(item.date))
        holder.description.text = item.shortDescription

        if (item.logo == null) {
            holder.logo.gone()
        } else {
            imageLoader.display(item.logo!!, holder.logo)
            holder.logo.visible()
        }

        holder.itemView.tag = item
        holder.itemView.setOnClickListener(clickListener)
    }

}

