package com.dani2pix.rssrecipr.algorithm

import android.text.Html
import android.text.TextUtils
import android.util.Log
import com.dani2pix.rssrecipr.dashboard.model.FeedEntry
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by dandomnica on 2018-04-18.
 */
class ClusteringUtils {


    companion object {
        private val stopWords: MutableList<String> = mutableListOf("a", "able", "about",
                "across", "after", "all", "almost", "also", "am", "among", "an",
                "and", "any", "are", "as", "at", "be", "because", "been", "but",
                "by", "can", "cannot", "could", "dear", "did", "do", "does",
                "either", "else", "ever", "every", "for", "from", "get", "got",
                "had", "has", "have", "he", "her", "hers", "him", "his", "how",
                "however", "i", "if", "in", "into", "is", "it", "its", "just",
                "least", "let", "like", "likely", "may", "me", "might", "most",
                "must", "my", "neither", "no", "nor", "not", "of", "off", "often",
                "on", "only", "or", "other", "our", "own", "rather", "said", "say",
                "says", "she", "should", "since", "so", "some", "than", "that",
                "the", "their", "them", "then", "there", "these", "they", "this",
                "tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
                "what", "when", "where", "which", "while", "who", "whom", "why",
                "will", "with", "would", "yet", "you", "your")

        private val stemmer: Stemmer = Stemmer()
        private val dictionary: MutableSet<String> = mutableSetOf()

        fun cleanContent(entries: MutableList<FeedEntry>) {

            val items = mutableListOf<FeedEntry>()
            items.addAll(entries)

            for ((index, value) in items.withIndex()) {
                val item = items[index]
                var content = item.description
                content = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY).toString()
                content = content.replace("\\d".toRegex(), "")
                content = content.replace("\\t".toRegex(), "")
                content = content.replace("\\n".toRegex(), "")
                content = content.replace("\\p{Punct}".toRegex(), "")
                content = content.replace("\\p{Sc}".toRegex(), "")
                content = content.replace("( )+".toRegex(), " ")
                content = content.toLowerCase()
                Log.d("ClusteringUtils", content)

                val cleanedContent = cleanDescription(content)
            }
        }

        private fun cleanDescription(content: String): String {
            val contentList: MutableList<String> = content.split("\\s+".toRegex()).toMutableList()
            for ((index, value) in contentList.withIndex()) {
                for (stopWord in stopWords) {
                    if (value == stopWord) {
                        contentList[index] = ""
                        break
                    }
                }
                if (contentList[index] != "") {
                    stemmer.add(value.toCharArray(), value.length)
                    stemmer.stem()
                    contentList[index] = stemmer.toString()
                    dictionary.add(value)
                }
            }
            contentList.removeAll(listOf(""))
            return contentList.toString()
        }
    }
}