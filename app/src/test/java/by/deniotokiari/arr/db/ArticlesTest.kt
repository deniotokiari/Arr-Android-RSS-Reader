package by.deniotokiari.arr.db

import android.content.Context
import androidx.work.WorkerParameters
import by.deniotokiari.arr.db.dao.ArticleDao
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.getValueBlocking
import by.deniotokiari.arr.worker.ArticlesFetchAndCacheWorker
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.InputStream

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ArticlesTest : RoomDbBaseTest() {

    private lateinit var dao: ArticleDao

    @Before
    fun init() {
        dao = db.articleDao()
    }

    @Test
    fun `article in date desc collection`() {
        val feed = RssFeed(
            "Test", "IT", "habr.com", "habr.com", null, 1
        )

        db.rssFeedDao().insert(feed)

        val worker = ArticlesFetchAndCacheWorker(Mockito.mock(Context::class.java), Mockito.mock(WorkerParameters::class.java))
        val stream: InputStream? = javaClass.classLoader?.getResourceAsStream("feed/habr.com")

        val result: ArticlesFetchAndCacheWorker.FeedXmlResult? = worker.parseXml(stream, feed)

        var articles: List<Article>? = result?.articles

        dao.insert(articles!!)

        articles = dao.getLastArticles(false).getValueBlocking()

        Assert.assertNotNull(articles)
        Assert.assertTrue(articles!![0].date!! > articles[1].date!!)
    }

}