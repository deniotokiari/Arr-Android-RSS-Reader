package by.deniotokiari.arr.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R

class MenuGroupFeedsViewHolder(root: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_menu_group_item, root, false)) {

    val icon: ImageView = itemView.findViewById(R.id.icon)
    val title: TextView = itemView.findViewById(R.id.title)
    val count: TextView = itemView.findViewById(R.id.count)

}
