package by.deniotokiari.arr.viewmodel

import android.content.Context
import by.deniotokiari.arr.db.AppDatabase
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
class OpmlImportRssFeedViewModelTest {

    @Test
    fun `opml file parsing to rss feed entities`() {
        val stream: InputStream? = javaClass.classLoader?.getResourceAsStream("opml/feeds.opml")

        Assert.assertNotNull(stream)

        val viewModel = OpmlImportRssFeedViewModel(Mockito.mock(Context::class.java), Mockito.mock(AppDatabase::class.java))

        val feeds: List<RssFeed>? = viewModel.parseOpml(stream)

        Assert.assertNotNull(feeds)
        Assert.assertTrue(feeds?.isEmpty() == false)

        val firstFeed: RssFeed = feeds!!.first()

        Assert.assertEquals("Открытый космос Зеленого кота", firstFeed.title)
        Assert.assertEquals("Science", firstFeed.group)

        val lastFeed: RssFeed = feeds.last()

        Assert.assertEquals("stalic1", lastFeed.title)
        Assert.assertEquals("", lastFeed.group)
    }

}