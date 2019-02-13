package by.deniotokiari.arr.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.Article
import by.deniotokiari.arr.db.entity.RssFeed
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

class ArticlesFetchAndCacheWorker(context: Context, params: WorkerParameters) : Worker(context, params), KoinComponent {

    override fun doWork(): Result = inputData.getLong(FEED_ID, DEFAULT_FEED_ID).let { id ->
        if (id == DEFAULT_FEED_ID) {
            return Result.failure()
        }

        val http: OkHttpClient by inject()
        val db: AppDatabase by inject()
        val articlePubDateFormat: SimpleDateFormat by inject(name = "article")

        processFeed(id, http, db, articlePubDateFormat)
    }

    private fun processFeed(id: Long, http: OkHttpClient, db: AppDatabase, format: SimpleDateFormat): Result {
        var feed: RssFeed? = null

        try {
            feed = getFeedById(id, db)

            val stream: InputStream? = getFeedArticlesInputStream(http, feed?.source)
            val result: FeedXmlResult? = parseXml(stream, feed, format)

            result?.also {
                val icon: String? = it.icon
                val articles: List<Article> = it.articles

                feed?.icon = icon

                feed?.also { item -> db.rssFeedDao().update(item) }

                db.articleDao().insert(articles)

                return Result.success()
            }
        } catch (e: Exception) {
            Log.e("LOG", "${feed?.source}: ${e.message}")
        }

        return Result.failure()
    }

    internal fun parseXml(stream: InputStream?, feed: RssFeed?, format: SimpleDateFormat): FeedXmlResult? =
        stream?.use {
            val xmlParserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlParser: XmlPullParser = xmlParserFactory.newPullParser()

            xmlParser.setInput(it, null)

            var eventType: Int = xmlParser.eventType
            val result: ArrayList<Article> = ArrayList()
            var icon: String? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName: String = xmlParser.name

                        when (tagName) {
                            IMAGE -> {
                                icon = getFeedIcon(xmlParser)

                            }
                            ITEM -> {
                                val article: Article? = getFeedItem(xmlParser, feed, format)

                                article?.also { item -> result.add(item) }
                            }
                        }
                    }
                }

                xmlParser.next()
                eventType = xmlParser.eventType
            }

            FeedXmlResult(icon, result)
        }

    private fun convertToUnixTime(pubDate: String?, format: SimpleDateFormat): Long {
        val date: Date = format.parse(pubDate)

        return date.time
    }

    private fun getFeedById(id: Long, db: AppDatabase): RssFeed? = db.rssFeedDao().feedById(id)

    private fun getFeedArticlesInputStream(http: OkHttpClient, uri: String?): InputStream? = uri?.let { url ->
        val request: Request = Request.Builder()
            .url(url)
            .build()

        val response: Response? = http.newCall(request)
            .execute()

        response?.body()
            ?.byteStream()
    }

    private fun getFeedIcon(xmlParser: XmlPullParser): String? {
        xmlParser.next()

        var tagName: String? = xmlParser.name

        while (tagName != IMAGE) {
            if (tagName == URL) {
                xmlParser.next()

                if (xmlParser.eventType == XmlPullParser.TEXT) {
                    return xmlParser.text
                }
            }

            xmlParser.next()

            tagName = xmlParser.name
        }

        return null
    }

    private fun getFeedItem(xmlParser: XmlPullParser, feed: RssFeed?, format: SimpleDateFormat): Article? {
        xmlParser.next()

        var tagName: String? = xmlParser.name

        var title: String? = null
        var description: String? = null
        var link: String? = null
        var date = 0L
        var creator: String? = null

        while (tagName != ITEM) {
            if (xmlParser.eventType != XmlPullParser.END_TAG) {
                when (tagName) {
                    TITLE -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            title = xmlParser.text
                        }
                    }
                    DESCRIPTION -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            description = xmlParser.text
                        }
                    }
                    LINK -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            link = xmlParser.text
                        }
                    }
                    DATE -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            date = convertToUnixTime(xmlParser.text, format)
                        }
                    }
                    CREATOR -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            creator = xmlParser.text
                        }
                    }
                }
            }

            xmlParser.next()

            tagName = xmlParser.name
        }

        return if (title != null && description != null) {
            //val pattern: Pattern = Pattern.compile("(?i)<img[^>]+?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")

            val article = Article(
                title = title,
                description = description,
                feedId = feed?.id,
                link = link,
                date = date,
                creator = creator
            )

            article
        } else {
            null
        }
    }

    internal data class FeedXmlResult(val icon: String?, val articles: List<Article>)

    companion object {

        const val FEED_ID = "ArticlesFetchAndCacheWorker:FEED_ID"

        private const val IMAGE = "image"
        private const val URL = "url"
        private const val ITEM = "item"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val LINK = "link"
        private const val DATE = "pubDate"
        private const val CREATOR = "dc:creator"

        private const val DEFAULT_FEED_ID = -1L

        fun getOneTimeRequest(id: Long): OneTimeWorkRequest {
            val data: Data = Data.Builder()
                .putLong(FEED_ID, id)
                .build()

            return OneTimeWorkRequestBuilder<ArticlesFetchAndCacheWorker>().setInputData(data).build()
        }

    }

}

