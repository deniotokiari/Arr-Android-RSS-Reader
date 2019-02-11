package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.Article

class ArticleDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_article_detail, container, false)

        val title: TextView = view.findViewById(R.id.title)
        val description: WebView = view.findViewById(R.id.description)

        description.apply {
            settings.javaScriptEnabled = true
        }

        getArticle()?.also {
            title.text = it.title
            description.loadDataWithBaseURL("", "<style>img{display: inline; height: auto; max-width: 100%;}</style>${it.description}", "text/html", "UTF-8", "")
        }

        return view
    }

    private fun getArticle(): Article? = arguments?.let { it[KEY_ARTICLE] as Article? }

    companion object {

        private const val KEY_ARTICLE = "KEY_ARTICLE"

        fun newInstance(article: Article): ArticleDetailFragment {
            val fragment = ArticleDetailFragment()
            val bundle = Bundle()

            bundle.putSerializable(KEY_ARTICLE, article)
            fragment.arguments = bundle

            return fragment
        }

    }

}