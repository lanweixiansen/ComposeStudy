package com.example.demoapplication

import org.junit.Test
import kotlin.math.max

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun rob(nums: IntArray): Int {
        var prev = 0
        var curr = 0
        for (i in nums) {
            val temp = max(curr, prev + i)
            prev = curr
            curr = temp
        }
        return curr
    }

    @Test
    fun b() {
        rob(intArrayOf(2,1,1,2))
    }
}