package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.viewmodel.MenuItem
import by.deniotokiari.arr.viewmodel.MenuViewModel
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModel()

    private lateinit var menuRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result: View = LayoutInflater.from(context).inflate(R.layout.fragment_menu, container, false)

        menuRecyclerView = result.findViewById(R.id.menu_recycler_view)

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        menuRecyclerView.adapter = GroupAdapter(ArrayList(), getString(R.string.UNCATEGORIZED))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        menuViewModel.getMenuItems().observe(this, Observer {
            val items: ArrayList<MenuItem>? = (menuRecyclerView.adapter as GroupAdapter?)?.items

            items?.clear()
            items?.addAll(it)

            menuRecyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private class GroupFeedsViewHolder(root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_menu_group_item, root, false)) {

        val icon: ImageView = itemView.findViewById(R.id.icon)
        val title: TextView = itemView.findViewById(R.id.title)
        val count: TextView = itemView.findViewById(R.id.count)

    }

    private class GroupFeedsAdapter : RecyclerView.Adapter<GroupFeedsViewHolder>() {

        val items: ArrayList<RssFeed> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupFeedsViewHolder = GroupFeedsViewHolder(parent)

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: GroupFeedsViewHolder, position: Int) {
            val item: RssFeed = items[position]

            holder.title.text = item.title
            holder.count.text = 0.toString()
        }

    }

    private class GroupViewHolder(root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_menu_item, root, false)) {

        val groupTitle: TextView = itemView.findViewById(R.id.group_title_text_view)
        val groupCount: TextView = itemView.findViewById(R.id.group_count_text_view)
        val groupFeeds: RecyclerView = itemView.findViewById(R.id.group_items)

        init {
            groupFeeds.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            groupFeeds.adapter = GroupFeedsAdapter()
        }

    }

    private class GroupAdapter(var items: ArrayList<MenuItem>?, private val uncategorized: String) : RecyclerView.Adapter<GroupViewHolder>() {

        private val onClickListener: View.OnClickListener = View.OnClickListener {
            val pair: Pair<*, *> = it.tag as Pair<*, *>

            val groupFeeds: RecyclerView = pair.first as RecyclerView
            val adapter: GroupFeedsAdapter = groupFeeds.adapter as GroupFeedsAdapter

            if (groupFeeds.visibility == View.VISIBLE) {
                groupFeeds.gone()

                adapter.items.clear()
                adapter.notifyDataSetChanged()
            } else {
                groupFeeds.visible()

                val item: MenuItem = pair.second as MenuItem

                adapter.items.clear()

                item.items?.also { items -> adapter.items.addAll(items) }

                adapter.notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder = GroupViewHolder(parent)

        override fun getItemCount(): Int = items?.size ?: 0

        override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
            holder.itemView.setOnClickListener(onClickListener)

            val item: MenuItem = items!![position]

            holder.itemView.tag = Pair(holder.groupFeeds, item)

            val title: String = if (item.title.isEmpty()) uncategorized else item.title

            holder.groupTitle.text = title
            holder.groupCount.text = item.count.toString()
        }

    }

}
