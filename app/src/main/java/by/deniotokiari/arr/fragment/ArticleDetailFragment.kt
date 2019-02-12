package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.Article

class ArticleDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_article_detail, container, false)

        val description: WebView = view.findViewById(R.id.description)

        description.apply {
            settings.javaScriptEnabled = true
        }

        getArticle()?.also {
            description.loadDataWithBaseURL(
                EMPTY_STRING,
                String.format(ARTICLE_DETAIL_HTML_TEMPLATE, it.title, it.description),
                MIME_TYPE,
                ENCODING,
                EMPTY_STRING
            )
        }

        return view
    }

    private fun getArticle(): Article? = arguments?.let { it[KEY_ARTICLE] as? Article }

    companion object {

        private const val ENCODING = "UTF-8"
        private const val MIME_TYPE = "text/html"
        private const val EMPTY_STRING = ""
        private const val ARTICLE_DETAIL_HTML_TEMPLATE = "<html><head><style>div,img{display: inline; height: auto; width: 100%%;}</style></head><body><div id=\"title\"><h2>%s</h2></div><div id=\"description\">%s</div></body></html>"
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