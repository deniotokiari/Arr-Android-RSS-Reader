package by.deniotokiari.arr.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.viewmodel.ArticleReadabilityDetailsViewModel
import by.deniotokiari.core.coroutines.bg
import by.deniotokiari.core.coroutines.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.dankito.readability4j.Readability4J
import net.dankito.readability4j.extended.Readability4JExtended
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArticleDetailFragment : Fragment() {

    private val http: OkHttpClient by inject()

    private val article: Article? by lazy { arguments?.let { it[KEY_ARTICLE] as? Article } }

    private val articleReadabilityDetailsViewModel: ArticleReadabilityDetailsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_article_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView: WebView = view.findViewById(R.id.description)

        webView.apply {
            @SuppressLint("SetJavaScriptEnabled")
            settings.javaScriptEnabled = true
        }

        // TODO: should be part of viewModel
        articleReadabilityDetailsViewModel.getReadability().observe(this, Observer { readability ->
            article?.also {
                val link: String? = it.link

                if (readability && link != null) {
                    GlobalScope.launch(bg) {
                        val description: String = getReadabilityContent(link) ?: it.description

                        withContext(ui) { loadContent(webView, it.title, description) }
                    }
                } else {
                    loadContent(webView, it.title, it.description)
                }
            }
        })
    }

    private fun getReadabilityContent(link: String): String? {
        val request: Request = Request.Builder()
            .get()
            .url(link)
            .build()

        return http.newCall(request).execute().body()?.string()
            ?.let { html ->
                val readability4J: Readability4J = Readability4JExtended(link, html)
                val parsedContent: net.dankito.readability4j.Article = readability4J.parse()

                parsedContent.contentWithUtf8Encoding
            }
    }

    private fun loadContent(webView: WebView, title: String, description: String) {
        webView.loadDataWithBaseURL(
            EMPTY_STRING,
            String.format(ARTICLE_DETAIL_HTML_TEMPLATE, title, description),
            MIME_TYPE,
            ENCODING,
            EMPTY_STRING
        )
    }

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