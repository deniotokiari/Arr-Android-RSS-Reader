package by.deniotokiari.arr.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R

class LastArticleViewHolder(root: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(root.context).inflate(R.layout.adapter_title_article, root, false)) {

    val title: TextView = itemView.findViewById(R.id.title)

}