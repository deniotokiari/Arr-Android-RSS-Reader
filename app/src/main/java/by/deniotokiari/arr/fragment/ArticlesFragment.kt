package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.deniotokiari.arr.R
import by.deniotokiari.arr.adapter.ArticlesAdapter
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewmodel.ArticlesViewModel
import by.deniotokiari.core.extensions.gone
import by.deniotokiari.core.extensions.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class ArticlesFragment : Fragment() {

    private val articlesViewModel: ArticlesViewModel by viewModel()
    private val articlePublishDateFormat: SimpleDateFormat by inject("article_adapter")

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_articles, container, false)

        recyclerView = view.findViewById(R.id.articles_recycler_vew)
        recyclerView.adapter = ArticlesAdapter(articlePublishDateFormat, getString(R.string.CREATOR_AND_PUBLISH_DATE))

        titleTextView = view.findViewById(R.id.title)

        val title: String? = getTitle()

        if (title != null) {
            titleTextView.text = title

            titleTextView.visible()
        } else {
            titleTextView.gone()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val feedId: Long? = getFeedId()
        val articlesLiveData: LiveData<List<Article>> = if (feedId == null) articlesViewModel.getLastArticles() else articlesViewModel.getLastArticlesById(feedId)

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

    private fun getTitle(): String? = arguments?.let { bundle -> bundle.getString(KEY_TITLE) }

    companion object {

        private const val KEY_ID = "KEY_ID"
        private const val KEY_TITLE = "KEY_TITLE"

        fun newIntance(id: Long? = null, title: String): ArticlesFragment {
            val fragment = ArticlesFragment()
            val bundle = Bundle()

            id?.also { bundle.putLong(KEY_ID, id) }

            bundle.putString(KEY_TITLE, title)

            fragment.arguments = bundle

            return fragment
        }

    }

}