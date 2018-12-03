package by.deniotokiari.arr.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R

class MenuGroupViewHolder(adapter: RecyclerView.Adapter<*>, root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_menu_item, root, false)) {

    val groupTitle: TextView = itemView.findViewById(R.id.group_title_text_view)
    val groupCount: TextView = itemView.findViewById(R.id.group_count_text_view)
    val groupFeeds: RecyclerView = itemView.findViewById(R.id.group_items)

    init {
        groupFeeds.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        groupFeeds.adapter = adapter
    }

}