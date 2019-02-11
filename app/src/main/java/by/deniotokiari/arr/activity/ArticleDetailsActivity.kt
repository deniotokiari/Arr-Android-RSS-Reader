package by.deniotokiari.arr.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import by.deniotokiari.arr.R
import by.deniotokiari.arr.adapter.ArticleDetailStateFragmentViewPagerAdapter
import by.deniotokiari.arr.viewmodel.ArticlesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailsActivity : AppCompatActivity() {

    private val articlesViewModel: ArticlesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_article_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val viewPager: ViewPager = findViewById(R.id.viewPager)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        articlesViewModel.getArticles(getFeedId()).observe(this, Observer {
            val adapter = ArticleDetailStateFragmentViewPagerAdapter(supportFragmentManager, it)

            val currentArticleTitle: String = getArticleTitle()
            val currentArticlePublishDate: Long = getArticlePublishDate()

            // TODO: need to modify to binary search
            val currentArticlePosition: Int? = it.find { article -> article.title == currentArticleTitle && article.date == currentArticlePublishDate }?.let { article -> it.indexOf(article) }

            viewPager.adapter = adapter

            currentArticlePosition?.also { position ->
                viewPager.currentItem = position
            }
        })
    }

    private fun getArticleTitle(): String = intent.getStringExtra(EXTRA_KEY_TITLE)

    private fun getArticlePublishDate(): Long = intent.getLongExtra(EXTRA_KEY_PUBLISH_DATE, NO_VALUE)

    private fun getFeedId(): Long? {
        val result: Long = intent.getLongExtra(EXTRA_KEY_FEED_ID, NO_VALUE)

        return if (result == NO_VALUE) {
            null
        } else {
            result
        }
    }

    companion object {

        private const val EXTRA_KEY_TITLE = "EXTRA_KEY_TITLE"
        private const val EXTRA_KEY_PUBLISH_DATE = "EXTRA_KEY_PUBLISH_DATE"
        private const val EXTRA_KEY_FEED_ID = "EXTRA_KEY_FEED_ID"

        private const val NO_VALUE = -1L

        fun start(context: Context, articleTitle: String, articlePublishDate: Long, feedId: Long? = null) {
            val intent = Intent(context, ArticleDetailsActivity::class.java)

            intent.putExtra(EXTRA_KEY_TITLE, articleTitle)
            intent.putExtra(EXTRA_KEY_PUBLISH_DATE, articlePublishDate)
            intent.putExtra(EXTRA_KEY_FEED_ID, feedId)

            context.startActivity(intent)
        }

    }

}