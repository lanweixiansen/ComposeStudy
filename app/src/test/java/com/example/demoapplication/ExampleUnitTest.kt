package com.example.demoapplication

import org.junit.Test
import java.util.LinkedList

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
        val point1 = System.currentTimeMillis()
        val viewQueue = ViewLinkList<Int>()
        viewQueue.add(1)
        viewQueue.add(2)
        viewQueue.add(3)
        viewQueue.add(4)
        val point2 = System.currentTimeMillis()
        println("content: $viewQueue  runtime: ${point2 - point1}")
    }
}