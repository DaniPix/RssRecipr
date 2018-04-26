package com.dani2pix.rssrecipr

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun firstOneMillionNumbers() {
        for (i in 1..1000000) {
            print(i)
        }

        assertTrue(true)
    }
}
