package by.deniotokiari.arr.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.deniotokiari.arr.R
import by.deniotokiari.arr.db.entity.Article

class ArticleDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_article_detail, container, false)

        val titleTextView: TextView = view.findViewById(R.id.title)

        getArticle()?.also {
            titleTextView.text = it.title
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