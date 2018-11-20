package by.deniotokiari.arr.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.extentsion.getAttribute
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.InputStream

class OpmlImportRssFeedViewModel(private val context: Context, private val db: AppDatabase) : ViewModel() {

    private var feeds: MutableLiveData<List<RssFeed>> = MutableLiveData()
    private var job: Job? = null

    override fun onCleared() {
        job?.cancel()
    }

    internal fun parseOpml(stream: InputStream?): List<RssFeed>? {
        return stream
            ?.use { it.readBytes() }
            ?.let { byteArray ->
                fun getRssFeed(xmlParser: XmlPullParser, group: String?): RssFeed? {
                    val title: String? = xmlParser.getAttribute(ATTR_TITLE)
                    val xmlUrl: String? = xmlParser.getAttribute(ATTR_XML_URL)
                    val htmlUrl: String? = xmlParser.getAttribute(ATTR_HTML_URL)

                    if (group != null && title != null && xmlUrl != null && htmlUrl != null) {
                        return RssFeed(title, group, xmlUrl, htmlUrl)
                    }

                    return null
                }

                val result: ArrayList<RssFeed> = ArrayList()

                val xmlParserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                val xmlParser: XmlPullParser = xmlParserFactory.newPullParser()

                xmlParser.setInput(ByteArrayInputStream(byteArray, 0, byteArray.size), null)

                var eventType: Int = xmlParser.eventType
                var group: String? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName: String = xmlParser.name

                            when (tagName) {
                                TAG_OUTLINE -> {
                                    if (xmlParser.depth == DEPTH_OUTLINE_GROUP) {
                                        group = xmlParser.getAttribute(ATTR_TITLE)

                                        val type: String? = xmlParser.getAttribute(ATTR_TYPE)

                                        if (type == VAL_RSS) {
                                            getRssFeed(xmlParser, "")?.also { result.add(it) }
                                        }
                                    } else if (xmlParser.depth == DEPTH_OUTLINE_FEED) {
                                        getRssFeed(xmlParser, group)?.also { result.add(it) }
                                    }
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            val tagName: String = xmlParser.name

                            when (tagName) {
                                TAG_OUTLINE -> {
                                    if (xmlParser.depth == DEPTH_OUTLINE_GROUP) {
                                        group = null
                                    }
                                }
                            }
                        }
                    }

                    xmlParser.next()
                    eventType = xmlParser.eventType
                }

                result
            }
    }

    fun setFileUri(uri: Uri?) {
        fun getInputStream(context: Context, uri: Uri?): InputStream? = uri?.let { context.contentResolver.openInputStream(uri) }

        job?.cancel()

        job = GlobalScope.launch(bg) {
            val result: List<RssFeed>? = parseOpml(getInputStream(context, uri))

            result?.also {
                db.rssFeedDao().insert(it)
            }

            feeds.value = result
        }
    }

    fun getFeedsCount(): LiveData<Int> = Transformations.map(feeds) { it?.size }

    private companion object {

        const val TAG_OUTLINE = "outline"

        const val ATTR_TITLE = "title"
        const val ATTR_XML_URL = "xmlUrl"
        const val ATTR_HTML_URL = "htmlUrl"
        const val ATTR_TYPE = "type"

        const val VAL_RSS = "rss"

        const val DEPTH_OUTLINE_GROUP = 3
        const val DEPTH_OUTLINE_FEED = 4

    }

}