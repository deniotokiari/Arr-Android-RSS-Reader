package by.deniotokiari.arr.livedata

import android.content.Context
import android.net.Uri
import by.deniotokiari.arr.db.entity.RssFeed
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.InputStream

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class OpmlToRssFeedParsingTest {

    @Test
    fun `opml file parsing to rss feed entities`() {
        val stream: InputStream? = javaClass.classLoader?.getResourceAsStream("opml/feeds.opml")

        Assert.assertNotNull(stream)

        val data = RssFeedFromOpmlLiveData(Mockito.mock(Context::class.java), Mockito.mock(Uri::class.java))

        val feeds: List<RssFeed>? = data.parseOpml(stream)

        Assert.assertNotNull(feeds)
        Assert.assertTrue(feeds?.isEmpty() == false)

        val firstFeed: RssFeed = feeds!!.first()

        Assert.assertEquals("Открытый космос Зеленого кота", firstFeed.title)
        Assert.assertEquals("Science", firstFeed.group)

        val lastFeed: RssFeed = feeds.last()

        Assert.assertEquals("stalic", lastFeed.title)
        Assert.assertEquals("", lastFeed.group)
    }

}