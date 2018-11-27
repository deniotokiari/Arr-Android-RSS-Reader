package by.deniotokiari.arr.worker

import android.content.Context
import androidx.work.WorkerParameters
import by.deniotokiari.arr.db.RoomDbBaseTest
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.getValueBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.InputStream

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ArticlesFetchAndCacheWorkerTest : RoomDbBaseTest() {

    @Test
    fun `rss feed parsing and processing`() {
        val feed = RssFeed(
            "Test",
            "IT",
            "habr.com",
            "habr.com"
        )

        val id: Long = db.rssFeedDao().insert(feed)

        Assert.assertNotNull(id)

        val feedFromDb: RssFeed? = db.rssFeedDao().all().getValueBlocking()?.first()

        Assert.assertNotNull(feedFromDb)

        val worker = ArticlesFetchAndCacheWorker(Mockito.mock(Context::class.java), Mockito.mock(WorkerParameters::class.java))
        val stream: InputStream? = javaClass.classLoader?.getResourceAsStream("feed/habr.com")

        worker.parseXml(stream, feedFromDb, db)

        val articles: List<Article>? = db.articleDao().all().getValueBlocking()

        Assert.assertNotNull(articles)
        Assert.assertEquals(20, articles?.size)

        val updatedFeed: RssFeed? = db.rssFeedDao().feedById(id)

        Assert.assertNotNull(updatedFeed)
        Assert.assertNotNull(updatedFeed?.icon)
        Assert.assertEquals("https://habr.com/images/logo.png", updatedFeed?.icon)
    }

}