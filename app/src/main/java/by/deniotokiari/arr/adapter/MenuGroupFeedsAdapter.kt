package by.deniotokiari.arr.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewholder.MenuGroupFeedsViewHolder
import by.deniotokiari.arr.viewmodel.Feed
import by.deniotokiari.core.imageloader.IImageLoader

class MenuGroupFeedsAdapter(private val imageLoader: IImageLoader) : RecyclerView.Adapter<MenuGroupFeedsViewHolder>() {

    val items: ArrayList<Feed> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuGroupFeedsViewHolder = MenuGroupFeedsViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MenuGroupFeedsViewHolder, position: Int) {
        val item: Feed = items[position]
        val feed: RssFeed = item.feed

        holder.title.text = feed.title

        feed.icon?.also { imageLoader.display(it, holder.icon) }

        val count: Int = item.count

        if (count == 0) {
            holder.count.text = ""
        } else {
            holder.count.text = count.toString()
        }
    }

}