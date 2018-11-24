package by.deniotokiari.arr.livedata

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import by.deniotokiari.arr.db.entity.RssFeed
import by.deniotokiari.arr.extentsion.getAttribute
import by.deniotokiari.core.coroutines.bg
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class RssFeedFromOpmlLiveData(private val context: Context, private val uri: Uri) : LiveData<List<RssFeed>>() {

    private var job: Job? = null

    internal fun parseOpml(stream: InputStream?): List<RssFeed>? {
        return stream
            ?.use { stream ->
                fun getRssFeed(xmlParser: XmlPullParser, group: String? = ""): RssFeed? {
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

                xmlParser.setInput(stream, null)

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
                                            getRssFeed(xmlParser)?.also { result.add(it) }
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

    private fun getInputStream(context: Context, uri: Uri?): InputStream? = uri?.let { context.contentResolver.openInputStream(uri) }

    override fun onActive() {
        super.onActive()

        job?.cancel()

        job = GlobalScope.launch(bg) {
            val result: List<RssFeed>? = parseOpml(getInputStream(context, uri))

            postValue(result)
        }
    }

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