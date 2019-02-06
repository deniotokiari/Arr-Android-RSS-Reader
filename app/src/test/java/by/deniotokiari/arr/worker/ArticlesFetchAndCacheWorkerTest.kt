package by.deniotokiari.arr.worker

import android.content.Context
import androidx.work.WorkerParameters
import by.deniotokiari.arr.db.entity.RssFeed
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ArticlesFetchAndCacheWorkerTest {

    @Test
    fun `rss feed parsing and processing`() {
        val feed = RssFeed(
            "Test", "IT", "habr.com", "habr.com", null, 1
        )

        val worker = ArticlesFetchAndCacheWorker(Mockito.mock(Context::class.java), Mockito.mock(WorkerParameters::class.java))
        val stream: InputStream? = javaClass.classLoader?.getResourceAsStream("feed/habr.com")

        val result: ArticlesFetchAndCacheWorker.FeedXmlResult? = worker.parseXml(stream, feed, SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US))

        Assert.assertNotNull(result)
        Assert.assertEquals(20, result?.articles?.size)
        Assert.assertNotNull(result?.icon)
        Assert.assertEquals("https://habr.com/images/logo.png", result?.icon)
    }

}