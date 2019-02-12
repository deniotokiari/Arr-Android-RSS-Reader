package by.deniotokiari.arr.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import by.deniotokiari.arr.R
import by.deniotokiari.arr.adapter.ArticleDetailStateFragmentViewPagerAdapter
import by.deniotokiari.arr.viewmodel.ArticlesViewModel
import by.deniotokiari.core.extensions.get
import by.deniotokiari.core.extensions.set
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailsActivity : AppCompatActivity() {

    private val articlesViewModel: ArticlesViewModel by viewModel()
    private val prefs: SharedPreferences by inject()

    private lateinit var adapter: ArticleDetailStateFragmentViewPagerAdapter

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
            adapter = ArticleDetailStateFragmentViewPagerAdapter(supportFragmentManager, it)
        })

        articlesViewModel.getArticleIndex(getArticleTitle(), getArticlePublishDate()).observe(this, Observer {
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 1
            viewPager.currentItem = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article_details, menu)

        val readabilityOn: MenuItem? = menu?.findItem(R.id.action_readability_on)
        val readabilityOff: MenuItem? = menu?.findItem(R.id.action_readability_off)

        val readability: Boolean = prefs[KEY_READABILITY + getFeedId()]

        readabilityOff?.isVisible = readability
        readabilityOn?.isVisible = !readability

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }
            R.id.action_readability_on, R.id.action_readability_off -> {
                val readability: Boolean = prefs[KEY_READABILITY + getFeedId()]

                item.isVisible = false

                prefs[KEY_READABILITY + getFeedId()] = !readability

                invalidateOptionsMenu()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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

        private const val KEY_READABILITY = "KEY_READABILITY"

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