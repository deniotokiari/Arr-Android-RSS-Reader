package by.deniotokiari.arr.worker

import android.content.Context
import android.util.Log
import androidx.work.*
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

class ArticlesFetchAndCacheWorker(context: Context, params: WorkerParameters) : Worker(context, params), KoinComponent {

    override fun doWork(): Result = inputData
        .getLong(FEED_ID, DEFAULT_FEED_ID)
        .let { id ->
            if (id == DEFAULT_FEED_ID) {
                return Result.FAILURE
            }

            val http: OkHttpClient by inject()
            val db: AppDatabase by inject()

            val feed: RssFeed? = getFeedById(id, db)
            val stream: InputStream? = getFeedArticlesInputStream(http, feed?.source)

            try {
                val articles: List<Article>? = parseXml(stream, feed, db)

                articles?.also { db.articleDao().insert(articles) }

                Log.d("LOG", "${feed?.source}: ${articles?.size}")

                Result.SUCCESS
            } catch (e: Exception) {
                Log.e("LOG", "${feed?.source}")

                Result.FAILURE
            }
        }

    internal fun parseXml(stream: InputStream?, feed: RssFeed?, db: AppDatabase): List<Article>? = stream
        ?.use {
            val xmlParserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlParser: XmlPullParser = xmlParserFactory.newPullParser()

            xmlParser.setInput(it, null)

            var eventType: Int = xmlParser.eventType
            val result: ArrayList<Article> = ArrayList()

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName: String = xmlParser.name

                        when (tagName) {
                            IMAGE -> updateFeedIcon(xmlParser, feed, db)
                            ITEM -> {
                                val article: Article? = getFeedItem(xmlParser, feed)

                                article?.also { item -> result.add(item) }
                            }
                        }
                    }
                }

                xmlParser.next()
                eventType = xmlParser.eventType
            }

            result
        }

    private fun getFeedById(id: Long, db: AppDatabase): RssFeed? = db.rssFeedDao().feedById(id)

    private fun getFeedArticlesInputStream(http: OkHttpClient, uri: String?): InputStream? = uri
        ?.let { url ->
            val request: Request = Request.Builder()
                .url(url)
                .build()

            val response: Response? = http.newCall(request).execute()

            response?.body()?.byteStream()
        }

    private fun updateFeedIcon(xmlParser: XmlPullParser, feed: RssFeed?, db: AppDatabase) {
        xmlParser.next()

        var tagName: String? = xmlParser.name

        while (tagName != IMAGE) {
            if (tagName == URL) {
                xmlParser.next()

                if (xmlParser.eventType == XmlPullParser.TEXT) {
                    feed?.icon = xmlParser.text

                    feed?.let { db.rssFeedDao().update(it) }

                    break
                }
            }

            xmlParser.next()

            tagName = xmlParser.name
        }
    }

    private fun getFeedItem(xmlParser: XmlPullParser, feed: RssFeed?): Article? {
        xmlParser.next()

        var tagName: String? = xmlParser.name

        var title: String? = null
        var description: String? = null
        var link: String? = null
        var date: String? = null
        var creator: String? = null
        val categories: ArrayList<String> = ArrayList()

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
                            date = xmlParser.text
                        }
                    }
                    CREATOR -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            creator = xmlParser.text
                        }
                    }
                    CATEGORY -> {
                        xmlParser.next()

                        if (xmlParser.eventType == XmlPullParser.TEXT) {
                            categories.add(xmlParser.text)
                        }
                    }
                }
            }

            xmlParser.next()

            tagName = xmlParser.name
        }

        return if (title != null && description != null) {
            val article = Article(
                title = title,
                description = description,
                category = categories.joinToString(","),
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
        private const val CATEGORY = "category"

        private const val DEFAULT_FEED_ID = -1L

        fun getOneTimeRequest(id: Long): OneTimeWorkRequest {
            val data: Data = Data.Builder().putLong(FEED_ID, id).build()

            return OneTimeWorkRequestBuilder<ArticlesFetchAndCacheWorker>().setInputData(data).build()
        }

    }

}

