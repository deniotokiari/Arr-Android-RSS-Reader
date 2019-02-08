package by.deniotokiari.arr.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewholder.MenuGroupFeedsViewHolder
import by.deniotokiari.arr.viewmodel.Feed
import by.deniotokiari.core.imageloader.IImageLoader
import by.deniotokiari.core.recyclerview.OnItemClickListener

class MenuGroupFeedsAdapter(private val imageLoader: IImageLoader, private val itemClickListener: OnItemClickListener<RssFeed>) : RecyclerView.Adapter<MenuGroupFeedsViewHolder>() {

    private val clickListener: View.OnClickListener = View.OnClickListener {
        val pair: Pair<*, *> = it.tag as Pair<*, *>
        val position: Int = pair.first as Int
        val item: RssFeed = pair.second as RssFeed

        itemClickListener.onItemClick(position, item)
    }

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

        holder.itemView.tag = Pair(position, feed)
        holder.itemView.setOnClickListener(clickListener)
    }

}