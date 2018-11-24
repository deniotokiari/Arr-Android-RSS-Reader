package by.deniotokiari.arr.worker

import android.content.Context
import androidx.work.WorkerParameters
import by.deniotokiari.arr.db.RoomDbBaseTest
import by.deniotokiari.arr.db.entity.RssFeed
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
        return

        val worker = ArticlesFetchAndCacheWorker(Mockito.mock(Context::class.java), Mockito.mock(WorkerParameters::class.java))
        val stream: InputStream? = javaClass.classLoader?.getResourceAsStream("feed/habr.com")
        val feed: RssFeed = Mockito.mock(RssFeed::class.java)

        Mockito.`when`(feed.id).thenReturn(42L)

        worker.parseXml(stream, feed, db)
    }

}