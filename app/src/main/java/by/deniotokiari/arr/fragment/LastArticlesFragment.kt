package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.adapter.LastArticlesAdapter
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewmodel.LastArticlesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LastArticlesFragment : Fragment() {

    private val lastArticlesViewModel: LastArticlesViewModel by sharedViewModel()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_last_articles, container, false)

        recyclerView = view.findViewById(R.id.articles_recycler_vew)
        recyclerView.adapter = LastArticlesAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lastArticlesViewModel.getArticles().observe(this, Observer { newItems ->
            val oldItems: ArrayList<Article>? = ((recyclerView.adapter) as? LastArticlesAdapter)?.items

            oldItems?.clear()
            oldItems?.addAll(newItems)

            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

}