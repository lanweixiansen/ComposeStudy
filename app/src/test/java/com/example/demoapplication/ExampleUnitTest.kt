package com.example.demoapplication

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.LinkedList
import java.util.Objects

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    class ViewLinkList<T> : LinkedList<T>() {
        override fun add(element: T): Boolean {
            if (this.size >= 3) {
                this.removeAt(0)
            }
            return super.add(element)
        }
    }

    @Test
    fun main() {
        val point2 = System.currentTimeMillis()
        val date = Date(point2)
        val pattern = "HH mm ss"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateStr = simpleDateFormat.format(date)
        val timeArray = LinkedList<Int>()
        dateStr.forEachIndexed { index, it ->
            if (it.toString().isNotBlank()) {
                timeArray.add(it.toString().toInt())
            }
        }

        println("time: $timeArray  ")

    }
}