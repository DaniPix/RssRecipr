package com.dani2pix.rssrecipr.util

import com.dani2pix.rssrecipr.dashboard.model.FeedEntry
import kotlinx.coroutines.experimental.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

/**
 * Created by dandomnica on 2018-04-10.
 */
class XmlParser {
    companion object {

        /**
         * Only Atom feeds are supported.
         */
        fun parseRssEntries(source: String) = async(CommonPool) {
            val items = mutableListOf<FeedEntry>()
            val inputStream = URL(source).openStream()

            try {
                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                val xmlPullParser: XmlPullParser = factory.newPullParser()
                xmlPullParser.setInput(inputStream, null)

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

                    if (name.equals("title", true)) {
                        title = result
                    } else if (name.equals("link", true)) {
                        link = result
                    } else if (name.equals("content", true)) {
                        description = result
                    }

                    if (title != null && link != null && description != null) {
                        if (isItem) {
                            val entry = FeedEntry(title, link, description)
                            items.add(entry)
                        }
                        title = null
                        link = null
                        description = null
                    }
                }

                return@async items

            } finally {
                inputStream.close()
            }
        }
    }
}