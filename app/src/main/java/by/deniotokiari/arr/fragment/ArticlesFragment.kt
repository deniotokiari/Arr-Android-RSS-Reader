package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.activity.ArticleDetailsActivity
import by.deniotokiari.arr.adapter.ArticlesAdapter
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewmodel.ArticlesViewModel
import by.deniotokiari.core.imageloader.IImageLoader
import by.deniotokiari.core.recyclerview.OnItemClickListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class ArticlesFragment : Fragment() {

    private val articlesViewModel: ArticlesViewModel by viewModel()
    private val articlePublishDateFormat: SimpleDateFormat by inject("article_adapter")
    private val imageLoader: IImageLoader by inject()

    private val itemClickListener: OnItemClickListener<Article> = object : OnItemClickListener<Article> {
        override fun onItemClick(position: Int, item: Article) {
            context?.also {
                ArticleDetailsActivity.start(it, item.title, item.date, getFeedId())
            }
        }
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_articles, container, false)

        recyclerView = view.findViewById(R.id.articles_recycler_vew)
        recyclerView.adapter = ArticlesAdapter(articlePublishDateFormat, getString(R.string.CREATOR_AND_PUBLISH_DATE), itemClickListener, imageLoader)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val feedId: Long? = getFeedId()
        val articlesLiveData: LiveData<List<Article>> = articlesViewModel.getLastArticles(feedId)

        articlesLiveData.observe(this, Observer { newItems ->
            val oldItems: ArrayList<Article>? = ((recyclerView.adapter) as? ArticlesAdapter)?.items

            oldItems?.clear()
            oldItems?.addAll(newItems)

            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun getFeedId(): Long? = arguments?.let { bundle ->
        if (bundle.containsKey(KEY_ID)) {
            bundle.getLong(KEY_ID)
        } else {
            null
        }
    }

    companion object {

        private const val KEY_ID = "KEY_ID"

        fun newInstance(id: Long? = null): ArticlesFragment {
            val fragment = ArticlesFragment()
            val bundle = Bundle()

            id?.also { bundle.putLong(KEY_ID, id) }

            fragment.arguments = bundle

            return fragment
        }

    }

}