package com.dani2pix.rssrecipr

import com.dani2pix.rssrecipr.algorithm.ClusteringUtils
import com.dani2pix.rssrecipr.algorithm.ContentUtils
import com.dani2pix.rssrecipr.database.Articles
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

/**
 * Created by dandomnica on 2018-04-23.
 */
class ClusteringUtilsTest {

    private val articles = mutableListOf<Articles>()

    @Before
    fun setup() {
        val contents = mutableListOf<String>()

        contents.add("Google, Apple, Microsoft, Amazon, IBM, Facebook") //ID0
        contents.add("Google, Apple, Microsoft, IBM, Facebook") //ID1
        contents.add("I hate Google and I love Apple") //ID2
        contents.add("Apple sucks. Xiaomi is love.") //ID3
        contents.add("No one likes") //ID4
        contents.add("Making up sentences is hard Making up sentences is " +
                "hard Making up sentences is hard Making up sentences is hard") // ID5
        contents.add("hard Making up sentences is hard Making up sentences is hard" +
                "IBM commits suicide IBM commits suicide IBM commits suicide IBM") //ID6
        contents.add("IBM commits suicide") //ID7


        for (index in 0..7) {
            articles.add(Articles("ID$index", "Article$index", "Link$index", contents[index]))
        }
    }

    @Test
    fun testAlgorithmReliability() {
        val contentToCluster = ContentUtils.prepareEntriesForClustering(articles)
        val dictionary = ContentUtils.generateDictionary(contentToCluster)
        val clusterIdList = ClusteringUtils.generateSimilarityMatrix(contentToCluster, dictionary, 5)

        for (cluster in clusterIdList) {
            println(cluster)
        }

        Assert.assertTrue(true)
    }

    @Test
    fun testCoroutines() {
        val job = launch(CommonPool) {
            for (i in 1..10) {
                if (!isActive) {
                    break
                }
                delay(3000)
            }
        }

        job.cancel()
    }

    @Test
    fun testKotlinCollectionsSequences(){
        val listOfGames = mutableListOf<String>()
        for(i in 1..100){
            listOfGames.add("Starcraft$i")
        }

        val listOfUpdatedGames =
                listOfGames
                        .asSequence()
                        .filter { it.endsWith("0") }
                        .take(5)
                        .toList()

        for(game in listOfUpdatedGames){
            println(game)
        }
        Assert.assertTrue(true)
    }
}