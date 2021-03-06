package com.dani2pix.rssrecipr.algorithm

import android.text.Html
import com.dani2pix.rssrecipr.database.Articles

class ContentUtils {
    companion object {
        /**
         * Stop words necessary to be trimmed from the content of articles as they will have
         * a negative impact in how reliable the algorithm will be, because these words are usually
         * present in most English articles as they are the most common and used English words.
         */
        private val STOP_WORDS: List<String> = listOf("a", "able", "about", "across", "after",
                "all", "almost", "also", "am", "among", "an", "and", "any", "are", "as", "at",
                "be", "because", "been", "but", "by", "can", "cannot", "could", "dear", "did",
                "do", "does", "either", "else", "ever", "every", "for", "from", "get", "got",
                "had", "has", "have", "he", "her", "hers", "him", "his", "how", "however", "i",
                "if", "in", "into", "is", "it", "its", "just", "least", "let", "like", "likely",
                "may", "me", "might", "most", "must", "my", "neither", "no", "nor", "not", "of",
                "off", "often", "on", "only", "or", "other", "our", "own", "rather", "said", "say",
                "says", "she", "should", "since", "so", "some", "than", "that", "the", "their",
                "them", "then", "there", "these", "they", "this", "tis", "to", "too", "twas", "us",
                "wants", "was", "we", "were", "what", "when", "where", "which", "while", "who",
                "whom", "why", "will", "with", "would", "yet", "you", "your")

        private val stemming: Stemmer = Stemmer()


        fun prepareEntriesForClustering(articles: List<Articles>): MutableList<Articles> {
            val entriesToCluster = mutableListOf<Articles>()

            for (article in articles) {
                val contentToTransform = removeStopWordsAndApplyWordStemming(cleanContentFromPunctuationAndHtmlTags(article.articleContent))
                article.contentToTransform = contentToTransform
                entriesToCluster.add(article)
            }

            return entriesToCluster
        }

        /**
         * Attempt to remove punctuation and HTML tags
         */
        private fun cleanContentFromPunctuationAndHtmlTags(articleContent: String): String {

            var content = articleContent

            //content = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY).toString()
            content = content.replace("\\d".toRegex(), "")
            content = content.replace("\\t".toRegex(), "")
            content = content.replace("\\n".toRegex(), "")
            content = content.replace("\\p{Punct}".toRegex(), "")
            content = content.replace("\\p{Sc}".toRegex(), "")
            content = content.replace("( )+".toRegex(), " ")
            content = content.toLowerCase()

            return content
        }

        /**
         * Remove stop words from content and apply stemming to words
         */
        private fun removeStopWordsAndApplyWordStemming(content: String): MutableList<String> {

            val contentList = content.split("\\s+".toRegex()).toMutableList()

            // replace every stop word with an empty string value
            for ((index, value) in contentList.withIndex()) {
                for (stopWord in STOP_WORDS) {
                    if (value == stopWord) {
                        // stop words will be replaced with an empty string
                        contentList[index] = ""
                        break
                    }
                }

                // apply word stemming for each word in content array
                if (contentList[index] != "") {
                    stemming.add(value.toCharArray(), value.length)
                    stemming.stem()
                    contentList[index] = stemming.toString()
                }
            }

            // remove all empty strings
            contentList.removeAll(listOf(""))

            return contentList
        }

        fun generateDictionary(articles: MutableList<Articles>): MutableSet<String> {
            val dictionary = mutableSetOf<String>()
            for (article in articles) {
                for (word in article.contentToTransform) {
                    dictionary.add(word)
                }
            }
            return dictionary
        }
    }
}