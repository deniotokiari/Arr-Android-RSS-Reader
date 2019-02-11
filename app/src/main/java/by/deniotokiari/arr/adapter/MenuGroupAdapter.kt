package by.deniotokiari.arr.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewholder.MenuGroupViewHolder
import by.deniotokiari.arr.viewmodel.Feed
import by.deniotokiari.arr.viewmodel.MenuItem
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.visible
import by.deniotokiari.core.imageloader.IImageLoader
import by.deniotokiari.core.recyclerview.OnItemClickListener

class MenuGroupAdapter(var items: ArrayList<MenuItem>?, private val imageLoader: IImageLoader, private val itemClickListener: OnItemClickListener<RssFeed>) : RecyclerView.Adapter<MenuGroupViewHolder>() {

    private val expandedGroups: HashMap<String, Boolean> = HashMap()

    private fun initGroupFeeds(view: RecyclerView, item: MenuItem) {
        val adapter = view.adapter as MenuGroupFeedsAdapter

        item.items?.also {
            val diffUtilCallback = FeedItemsDiffUtilCallback(it, adapter.items)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)

            adapter.items.clear()
            adapter.items.addAll(it)

            diffResult.dispatchUpdatesTo(adapter)
        }

        view.visible()
    }

    private fun releaseGroupFeeds(view: RecyclerView) {
        view.gone()

        val adapter = view.adapter as MenuGroupFeedsAdapter

        adapter.items.clear()
        adapter.notifyDataSetChanged()
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        val pair: Pair<*, *> = it.tag as Pair<*, *>
        val groupFeeds: RecyclerView = pair.first as RecyclerView
        val menuItem = pair.second as MenuItem

        if (menuItem.reserved) {
            itemClickListener.onItemClick(-1, RssFeed(menuItem.title, "", "", "", ""))
        } else {
            if (groupFeeds.visibility == View.VISIBLE) {
                expandedGroups.remove(menuItem.title)

                releaseGroupFeeds(groupFeeds)
            } else {
                expandedGroups[menuItem.title] = true

                initGroupFeeds(groupFeeds, menuItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuGroupViewHolder = MenuGroupViewHolder(MenuGroupFeedsAdapter(imageLoader, itemClickListener), parent)

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: MenuGroupViewHolder, position: Int) {
        holder.itemView.setOnClickListener(onClickListener)

        val item: MenuItem = items!![position]

        holder.itemView.tag = Pair(holder.groupFeeds, item)

        holder.groupTitle.text = item.title

        val count = item.count

        if (count == 0) {
            holder.groupCount.text = ""
        } else {
            holder.groupCount.text = count.toString()
        }

        if (expandedGroups.containsKey(item.title)) {
            initGroupFeeds(holder.groupFeeds, item)
        } else {
            releaseGroupFeeds(holder.groupFeeds)
        }
    }

}

private class FeedItemsDiffUtilCallback(val newItems: List<Feed>, val oldItems: List<Feed>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Feed = oldItems[oldItemPosition]
        val newItem: Feed = newItems[newItemPosition]

        return oldItem.feed.title == newItem.feed.title
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Feed = oldItems[oldItemPosition]
        val newItem: Feed = newItems[newItemPosition]

        return oldItem.count == newItem.count
                && oldItem.feed.group == newItem.feed.group
                && oldItem.feed.title == newItem.feed.title
                && oldItem.feed.icon == newItem.feed.icon
                && oldItem.feed.source == newItem.feed.source
    }
}