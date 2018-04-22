package com.dani2pix.rssrecipr.util

import android.content.Context
import android.util.Log
import com.dani2pix.rssrecipr.Injection
import com.dani2pix.rssrecipr.database.Articles
import kotlinx.coroutines.experimental.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.net.URL

/**
 * Created by dandomnica on 2018-04-10.
 */
class XmlParser {
    companion object {
        /**
         * Only Atom feeds are supported.
         */
        fun parseRssEntries(sources: List<String>, context: Context?) =
                async(CommonPool + CoroutineExceptionHandler { _, throwable ->
                    Log.e("Something went wrong", throwable.message, throwable)
                }) {
                    val items = mutableListOf<Articles>()
                    for (source in sources) {
                        var inputStream: InputStream? = null
                        try {
                            inputStream = URL(source).openStream()
                            val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                            val xmlPullParser: XmlPullParser = factory.newPullParser()
                            xmlPullParser.setInput(inputStream, null)

                            var articleId: String? = null
                            var title: String? = null
                            var link: String? = null
                            var description: String? = null
                            var isItem = false

                            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                                val eventType = xmlPullParser.eventType
                                val name = xmlPullParser.name
                                name ?: continue

                                if (eventType == XmlPullParser.END_TAG) {
                                    if (name.equals("entry", true)) {
                                        isItem = false
                                    }
                                    continue
                                }

                                if (eventType == XmlPullParser.START_TAG) {
                                    if (name.equals("entry", true)) {
                                        isItem = true
                                        continue
                                    }
                                }

                                var result = ""
                                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                                    result = xmlPullParser.text
                                    xmlPullParser.nextTag()
                                }

                                when (name) {
                                    "id" -> articleId = result
                                    "title" -> title = result
                                    "link" -> link = result
                                    "content" -> description = result
                                }

                                if (title != null && link != null && description != null && articleId != null && !articleId.endsWith(".xml")) {
                                    if (isItem) {
                                        val entry = Articles(articleId, title, link, description)
                                        items.add(entry)
                                    }
                                    articleId = null
                                    title = null
                                    link = null
                                    description = null
                                }
                            }

                            Injection.provideArticlesDataSource(context)?.insert(items)
                        } catch (exception: IOException) {
                            Log.e("Something went wrong", exception.message, exception)

                        } finally {
                            inputStream?.close()
                        }
                    }
                }
    }
}