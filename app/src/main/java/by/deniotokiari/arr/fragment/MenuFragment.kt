package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewmodel.Feed
import by.deniotokiari.arr.viewmodel.MenuItem
import by.deniotokiari.arr.viewmodel.MenuViewModel
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.visible
import com.bumptech.glide.RequestManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModel()
    private val glide: RequestManager by inject()

    private lateinit var menuRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result: View = LayoutInflater.from(context).inflate(R.layout.fragment_menu, container, false)

        menuRecyclerView = result.findViewById(R.id.menu_recycler_view)

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        menuRecyclerView.adapter = GroupAdapter(ArrayList(), glide)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        menuViewModel.getMenuItems().observe(this, Observer { newItems ->
            val oldItems: ArrayList<MenuItem>? = (menuRecyclerView.adapter as GroupAdapter?)?.items

            oldItems?.also {
                // TODO move to view model
                val diffUtilCallback = MenuItemsDiffUtilCallback(newItems, oldItems)
                val diffUtilResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallback)

                oldItems.clear()
                oldItems.addAll(newItems)

                menuRecyclerView.adapter?.also { adapter ->
                    diffUtilResult.dispatchUpdatesTo(adapter)
                }
            }
        })
    }

    private class MenuItemsDiffUtilCallback(val newItems: List<MenuItem>, val oldItems: List<MenuItem>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem: MenuItem = oldItems[oldItemPosition]
            val newItem: MenuItem = newItems[newItemPosition]

            return oldItem.title == newItem.title
        }

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem: MenuItem = oldItems[oldItemPosition]
            val newItem: MenuItem = newItems[newItemPosition]

            return oldItem.count == newItem.count
                    && oldItem.image == newItem.image
                    && oldItem.count == newItem.count
                    && oldItem.items?.size == newItem.items?.size
        }
    }

    private class GroupFeedsViewHolder(root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_menu_group_item, root, false)) {

        val icon: ImageView = itemView.findViewById(R.id.icon)
        val title: TextView = itemView.findViewById(R.id.title)
        val count: TextView = itemView.findViewById(R.id.count)

    }

    private class GroupFeedsAdapter(private val glide: RequestManager) : RecyclerView.Adapter<GroupFeedsViewHolder>() {

        val items: ArrayList<Feed> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupFeedsViewHolder = GroupFeedsViewHolder(parent)

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: GroupFeedsViewHolder, position: Int) {
            val item: Feed = items[position]
            val feed: RssFeed = item.feed

            holder.title.text = feed.title

            feed.icon?.also {
                glide
                    .load(it)
                    .into(holder.icon)
            }

            val count: Int = item.count

            if (count == 0) {
                holder.count.text = ""
            } else {
                holder.count.text = count.toString()
            }
        }

    }

    private class GroupViewHolder(glide: RequestManager, root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_menu_item, root, false)) {

        val groupTitle: TextView = itemView.findViewById(R.id.group_title_text_view)
        val groupCount: TextView = itemView.findViewById(R.id.group_count_text_view)
        val groupFeeds: RecyclerView = itemView.findViewById(R.id.group_items)

        init {
            groupFeeds.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            groupFeeds.adapter = GroupFeedsAdapter(glide)
        }

    }

    private class GroupAdapter(var items: ArrayList<MenuItem>?, private val glide: RequestManager) : RecyclerView.Adapter<GroupViewHolder>() {

        private val expandedGroups: HashMap<String, Boolean> = HashMap()

        private fun initGroupFeeds(view: RecyclerView, item: MenuItem) {
            val adapter = view.adapter as GroupFeedsAdapter

            adapter.items.clear()

            item.items?.also { adapter.items.addAll(it) }

            adapter.notifyDataSetChanged()

            view.visible()
        }

        private fun releaseGroupFeeds(view: RecyclerView) {
            view.gone()

            val adapter = view.adapter as GroupFeedsAdapter

            adapter.items.clear()
            adapter.notifyDataSetChanged()
        }

        private val onClickListener: View.OnClickListener = View.OnClickListener {
            val pair: Pair<*, *> = it.tag as Pair<*, *>
            val groupFeeds: RecyclerView = pair.first as RecyclerView
            val menuItem = pair.second as MenuItem

            if (groupFeeds.visibility == View.VISIBLE) {
                expandedGroups.remove(menuItem.title)

                releaseGroupFeeds(groupFeeds)
            } else {
                expandedGroups[menuItem.title] = true

                initGroupFeeds(groupFeeds, menuItem)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder = GroupViewHolder(glide, parent)

        override fun getItemCount(): Int = items?.size ?: 0

        override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
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

}
