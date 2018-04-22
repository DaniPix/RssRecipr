package com.dani2pix.rssrecipr.algorithm

import java.math.BigDecimal

/**
 * Created by dandomnica on 2018-04-11.
 */
class CosineSimilarity {

    companion object {
        fun cosineSimilarity(vector1: DoubleArray, vector2: DoubleArray): Double {

            var dotProduct = 0.0
            var magnitude1 = 0.0
            var magnitude2 = 0.0
            val cosineSimilarity: Double

            for (index in vector1.indices) {
                dotProduct += vector1[index] * vector2[index]
                magnitude1 += Math.pow(vector1[index], 2.0)
                magnitude2 += Math.pow(vector2[index], 2.0)
            }

            magnitude1 = Math.sqrt(magnitude1)
            magnitude2 = Math.sqrt(magnitude2)

            if (magnitude1 != 0.0 || magnitude2 != 0.0) {
                cosineSimilarity = dotProduct / (magnitude1 * magnitude2)
            } else {
                return 0.0
            }
            return roundTo2DecimalPlaces(1.0 - cosineSimilarity)
        }

        private fun roundTo2DecimalPlaces(number: Double): Double {
            return number.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
        }
    }
}