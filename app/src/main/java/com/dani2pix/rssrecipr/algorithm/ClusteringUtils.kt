package com.dani2pix.rssrecipr.algorithm

import com.dani2pix.rssrecipr.database.Articles

/**
 * Created by dandomnica on 2018-04-18.
 */
class ClusteringUtils {
    companion object {

        private val articlesIdList = mutableListOf<String>()
        /**
         * Build the similarity matrix for the articles
         */
        fun generateSimilarityMatrix(entries: MutableList<Articles>, numberOfClusters: Int) {
            articlesIdList.clear()
            for(entry in entries){
                articlesIdList.add(entry.articleId)
            }


            val frequencyList = buildFrequencyList(ContentUtils.cleanContent(entries))
            val similarityMatrix = Array(frequencyList.size) { Array(frequencyList.size) { 0.0 } }

            for ((rowIndex, rowVal) in frequencyList.withIndex()) {
                for ((colIndex, colVal) in frequencyList.withIndex()) {
                    if (rowIndex == colIndex) {
                        similarityMatrix[rowIndex][colIndex] = 0.0
                    } else {
                        similarityMatrix[rowIndex][colIndex] = CosineSimilarity.cosineSimilarity(rowVal, colVal)
                    }
                }
            }

            var iterations = 0
            while (iterations < numberOfClusters) {
                mergeClusters(similarityMatrix, frequencyList, entries)
                iterations++
            }
            articlesIdList.removeIf { it.equals("*") }
        }

        /**
         * Generate the frequency list for all articles
         * (e.g. 10 articles = 10 entries inside the frequency list)
         */
        private fun buildFrequencyList(entries: MutableList<Articles>): MutableList<DoubleArray> {

            val frequencyList: MutableList<DoubleArray> = mutableListOf()
            val dictionary = ContentUtils.getWordsDictionary()

            for (entry in entries) {
                val frequencyArray = DoubleArray(dictionary.size)
                for ((index, value) in entry.contentToTransform.withIndex()) {
                    if (dictionary.contains(value)) {
                        frequencyArray[index]++
                    }
                }
                frequencyList.add(frequencyArray)
            }

            return frequencyList
        }

        private fun mergeClusters(similarityMatrix: Array<Array<Double>>, frequencyList: MutableList<DoubleArray>, entries: MutableList<Articles>): Array<Array<Double>> {

            val minRowIndices = getMinIndices(true, frequencyList, similarityMatrix, entries)
            val minColIndices = getMinIndices(false, frequencyList, similarityMatrix, entries)

            val clusteredId = articlesIdList[minRowIndices] + "%%%" + articlesIdList[minColIndices]
            articlesIdList[minRowIndices] = clusteredId
            articlesIdList[minColIndices] = "*"

            var index = 0
            while (index < frequencyList.size / 2) {
                if (similarityMatrix[index][minRowIndices] > similarityMatrix[index][minColIndices]) {
                    similarityMatrix[index][minRowIndices] = similarityMatrix[index][minColIndices]
                }
                similarityMatrix[minRowIndices][index] = similarityMatrix[index][minRowIndices]
                index++
            }

            index = 0
            while (index < frequencyList.size / 2) {
                if (similarityMatrix[minRowIndices][index] > similarityMatrix[minColIndices][index]) {
                    similarityMatrix[minRowIndices][index] = similarityMatrix[minColIndices][index]
                }
                similarityMatrix[index][minRowIndices] = similarityMatrix[minRowIndices][index]
                index++
            }

            for (rowIndex in frequencyList.indices) {
                for (colIndex in frequencyList.indices) {
                    if (rowIndex == colIndex) {
                        similarityMatrix[rowIndex][colIndex] = 0.0
                    }
                }
            }

            // add zeroes instead of removing row and column
            for (indices in frequencyList.indices) {
                similarityMatrix[indices][minColIndices] = 0.0
                similarityMatrix[minColIndices][indices] = 0.0
            }
            return similarityMatrix
        }

        /**
         * Get the indices for the minimum values on row and column
         */
        private fun getMinIndices(isRow: Boolean, frequencyList: MutableList<DoubleArray>, similarityMatrix: Array<Array<Double>>, entries: MutableList<Articles>): Int {
            var infinity = Double.POSITIVE_INFINITY
            var minRowIndex = 0
            var minColIndex = 0

            for (rowIndex in frequencyList.indices) {
                for (colIndex in frequencyList.indices) {
                    if (similarityMatrix[rowIndex][colIndex] != 0.0) {
                        if (similarityMatrix[rowIndex][colIndex] < infinity) {
                            infinity = similarityMatrix[rowIndex][colIndex]
                            minRowIndex = rowIndex
                            minColIndex = colIndex
                        }
                    }
                }
            }
            return when (isRow) {
                true -> minRowIndex
                else -> minColIndex
            }
        }

        fun getClusteredGroups(): MutableList<String> {
            return articlesIdList
        }
    }
}