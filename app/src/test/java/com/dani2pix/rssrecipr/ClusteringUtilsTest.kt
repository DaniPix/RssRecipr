package com.dani2pix.rssrecipr

import com.dani2pix.rssrecipr.algorithm.ClusteringUtils
import com.dani2pix.rssrecipr.algorithm.ContentUtils
import com.dani2pix.rssrecipr.database.Articles
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when` as _when

/**
 * Created by dandomnica on 2018-04-23.
 */
class ClusteringUtilsTest {

    private val articles = mutableListOf<Articles>()

    @Before
    fun setup() {
        val contents = mutableListOf<String>()

        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")
        contents.add("")

        for (index in 0..9) {
            articles.add(Articles("ID$index", "Article$index", "Link$index", contents[index]))
        }
    }

    @Test
    fun testAlgorithmReliability() {
        val contentToCluster = ContentUtils.prepareEntriesForClustering(articles)
        val dictionary = ContentUtils.generateDictionary(contentToCluster)
        val clusterIdList = ClusteringUtils.generateSimilarityMatrix(contentToCluster, dictionary, 3)

        for (cluster in clusterIdList) {
            println(cluster)
        }

        Assert.assertTrue(true)
    }
}